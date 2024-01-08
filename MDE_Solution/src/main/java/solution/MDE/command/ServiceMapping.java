package solution.MDE.command;

import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.modelio.model.ITransaction;
import org.modelio.api.module.IModule;
import org.modelio.metamodel.uml.infrastructure.Stereotype;
import org.modelio.metamodel.uml.statik.Class;
import org.modelio.metamodel.uml.statik.Classifier;
import org.modelio.metamodel.uml.statik.Component;
import org.modelio.metamodel.uml.statik.Interface;
import org.modelio.metamodel.uml.statik.InterfaceRealization;

public class ServiceMapping extends GeneralMapping{
	
	
    private static final String InterfaceRealization = null;

	public void mapService(IModelingSession session, IModule module, org.modelio.metamodel.uml.statik.Package target, Class element) { 
		// Resgata o estereótipo do metamodelo "JavaClass" do módulo JavaDesigner
    	Stereotype javaClassStereotype = session.getMetamodelExtensions().getStereotype("JavaDesigner", "JavaClass", module.getModuleContext().getModelioServices().getMetamodelService().getMetamodel().getMClass(Class.class));
  
            try (ITransaction service = session.createTransaction("Process Service")) {
            	
            	// Cria uma classe estereootipada com "JavaClass" dentro do PSM, juntamente com uma interface que a classe implementa
            	
            	Class myClass = session.getModel().createClass(element.getName(), target, javaClassStereotype);
            	
            	Interface myInterface = session.getModel().createInterface("I" + element.getName(), target, null);
            	
            	// Cria uma InterfaceRealization
            	
                InterfaceRealization realization = session.getModel().createInterfaceRealization();
                
                // Define a relação de quem implementa e quem está sendo implementado
             
                realization.setImplementer(myClass);
                
                realization.setImplemented(myInterface);
                
                // Processa suas operações
                
                processOperations(session, module, myClass, element);   
                
                processOperations(session, module, myInterface, element); 
                
                // efetiva a transação
                
                service.commit();
                
            } catch (Exception e) {
            	// Reporta erro ao processar a transação
                module.getModuleContext().getLogService().error(e);
            }
        }
    }


