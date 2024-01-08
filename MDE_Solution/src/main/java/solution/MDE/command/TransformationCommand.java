package solution.MDE.command;

import java.util.List;
import org.eclipse.jface.dialogs.MessageDialog;
import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.module.IModule;
import org.modelio.api.module.command.DefaultModuleCommandHandler;
import org.modelio.vcore.smkernel.mapi.MObject;
import org.modelio.metamodel.uml.statik.Class;
import org.modelio.metamodel.uml.statik.Package;
import org.modelio.metamodel.uml.infrastructure.Stereotype;
import org.modelio.api.modelio.model.ITransaction;



public class TransformationCommand extends DefaultModuleCommandHandler {
	
    private static final String error = null;

    private org.modelio.metamodel.uml.statik.Package target;

	public TransformationCommand() {
        super();
    }

    @Override
    public boolean accept(List<MObject> selectedElements, IModule module) {
        return selectedElements.size() == 1;
    }
  
    
    @Override
    public void actionPerformed(List<MObject> selectedElements, IModule module) {
    	
    	// Resgata a sessão atual da Modelio
        IModelingSession session = module.getModuleContext().getModelingSession();

        // Resgata o elemento selecionado na aba de seleção da ferramenta, checando se ele foi executado em um pacote
        if (selectedElements.size() == 1 && selectedElements.get(0) instanceof org.modelio.metamodel.uml.statik.Package) {
            
            // Resgata a referência do pacote em que o comando é executado

        	org.modelio.metamodel.uml.statik.Package selectedPackage = (org.modelio.metamodel.uml.statik.Package) selectedElements.get(0);
  
            //Efetua o processamento com a referência do pacote selecionado
        	
            	processPackageContents(session, selectedPackage, module);
            	
            } else {
            	// Exibe uma mensagem de erro para o usuário, informando para selecionar um pacote
            	MessageDialog.openInformation(null, "ERROR", "Please Select a Package to run this script");
        }
    }

    public void processPackageContents(IModelingSession session, org.modelio.metamodel.uml.statik.Package sourcePackage, IModule module) {
    	
    	// Cria uma transação para efetuar mudanças dentro do modelo, é um passo obrigatório para que não gere erro quando feita a mudança no modelo, note que ele precisa de um "catch" para tratar um possivel erro na transação do modelo
        try (ITransaction t = session.createTransaction("Process Package Contents")) {
        	
         	// resgata os estereotipos do DDD dentro do metamodelo, criando uma referência de cada um deles
        	
            Stereotype entityStereotype = session.getMetamodelExtensions().getStereotype("LocalModule", "Entity", module.getModuleContext().getModelioServices().getMetamodelService().getMetamodel().getMClass(Class.class));
        	Stereotype valueObjectStereotype = session.getMetamodelExtensions().getStereotype("LocalModule", "ValueObject", module.getModuleContext().getModelioServices().getMetamodelService().getMetamodel().getMClass(Class.class));
        	Stereotype serviceStereotype = session.getMetamodelExtensions().getStereotype("LocalModule", "Service", module.getModuleContext().getModelioServices().getMetamodelService().getMetamodel().getMClass(Class.class));
         	Stereotype aggregateRootStereotype = session.getMetamodelExtensions().getStereotype("LocalModule", "AggregateRoot", module.getModuleContext().getModelioServices().getMetamodelService().getMetamodel().getMClass(Class.class));


        	// Dentro dessa função, crio o pacote "pacote selecionado + PSM" dentro do pacote selecionado, para inserir as novas classes criadas a partir do pacote selecionado, que seria o PIM

            target = session.getModel().createPackage(String.format("%s_PSM", sourcePackage.getName()), sourcePackage);
        	
        	// Dentro do pacote selecionado, itero entre seus elementos, resgatando somente as classes contidas dentro do pacote
            
            for (MObject element : sourcePackage.getCompositionChildren()) {
                if (element instanceof Class) {
                	
                	// efetuo a checagem para cada elemento do pacote, mapeando eles de forma diferente de acordo com o estereotipo do DDD
                	
                	if (entityStereotype != null && ((Class) element).isStereotyped(entityStereotype) || valueObjectStereotype != null && ((Class) element).isStereotyped(valueObjectStereotype)) {
                		EntityObjectValueMapping mapping = new EntityObjectValueMapping();
                		mapping.mapEntityObjectValue(session, module, target, (Class) element);
                	}
                   
                	if (serviceStereotype != null && ((Class) element).isStereotyped(serviceStereotype)) {
                		ServiceMapping mp = new ServiceMapping();
                		mp.mapService(session, module, target, (Class) element);
                	}
                	if (aggregateRootStereotype != null && ((Class) element).isStereotyped(aggregateRootStereotype)) {
                		AggregateMapping mp = new AggregateMapping();
                		mp.MapAggregate(session, module, target, (Class) element);
                	}

                }
            }
            t.commit();
        } catch (Exception e) {
        	// Reporta erro ao processar a transação
            module.getModuleContext().getLogService().error(e);
        }
    }
     

    
}