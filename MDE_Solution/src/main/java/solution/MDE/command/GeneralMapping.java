package solution.MDE.command;

import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.modelio.model.ITransaction;
import org.modelio.api.module.IModule;
import org.modelio.metamodel.uml.statik.Class;
import org.modelio.metamodel.uml.statik.Classifier;
import org.modelio.metamodel.uml.statik.Operation;
import org.modelio.vcore.smkernel.mapi.MObject;
import org.modelio.metamodel.uml.statik.Interface;


public class GeneralMapping implements ITarget, IOperations{
	
	@Override
    public void processOperations(IModelingSession session, IModule module, Classifier targetClassifier, MObject sourceElement) {
        try (ITransaction t = session.createTransaction("Process Operations")) {
        	// Itera entre as operações da classe analisada e cria as mesmas operações dentro da classe alvo (no caso, a que irá para o PSM)
            for (Operation sourceOperation : ((Classifier) sourceElement).getOwnedOperation()) {
            	
            	Operation newOperation = session.getModel().createOperation(sourceOperation.getName(), targetClassifier);
            	
            	// se o elemento em parâmetro for uma interface, seu método será abstrato
            	if(targetClassifier instanceof Interface) {
            		newOperation.setIsAbstract(true);
            	}
            }
         // efetiva a transação
            t.commit();
        } catch (Exception e) {
        	// Reporta erro ao processar a transação
            module.getModuleContext().getLogService().error(e);
        }
    }
	
	@Override
	public Class getElementinTarget(IModelingSession session, IModule module, org.modelio.metamodel.uml.statik.Package target, Class element) {
        
		// função auxiliar que percorre o pacote PSM criado anteriormente e procura o elemento tratado no loop dentro da classe TransformationCommand, para achar sua referência já criada estereotipada como "JavaClass"
		
		for (MObject x : target.getCompositionChildren()) {
            if (x instanceof Class) {
                if (element.getName().equals(x.getName())) {
                    return (Class) x;
                }
            }
        }
        return null; 
    }
	
}
