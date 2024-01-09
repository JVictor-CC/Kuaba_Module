package kuaba.Module.listeners;

import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.modelio.model.ITransaction;
import org.modelio.api.modelio.model.event.IModelChangeEvent;
import org.modelio.api.modelio.model.event.IModelChangeHandler;
import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.vcore.smkernel.mapi.MObject;
import kuaba.Module.impl.KuabaModule;

public class ModelChangeHandler implements IModelChangeHandler {

	@Override
	public void handleModelChange(IModelingSession session, IModelChangeEvent event) {
		try (ITransaction t = session.createTransaction("handle created elements")) {
			
			for (MObject createdElement : event.getCreationEvents ()) {
				// Check element status before update
				if ((createdElement != null) && createdElement.getStatus().isModifiable()) {
					ModelElement element = (ModelElement) createdElement;
					
					if (element.isStereotyped("KuabaModule", "suggestArrow")) {
						
						//MessageDialog.openInformation(null, "Sugestão", element.getName());
						
						//IModuleContext moduleContext = getModule().getModuleContext();
				        //IDiagramService diagramService = moduleContext.getModelioServices().getDiagramService();
                        
					} else {
						break;
					}
				}
			}
			t.commit();
		} catch (Exception e) {
			KuabaModule.getInstance().getModuleContext().getLogService().error(e);
		}
		
	}

}
