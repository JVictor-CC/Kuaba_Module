package kuaba.Module.diagram;

import org.modelio.api.modelio.diagram.IDiagramHandle;
import org.modelio.api.modelio.diagram.style.IStyleHandle;
import org.modelio.api.modelio.model.event.IModelChangeHandler;
import org.modelio.api.module.context.IModuleContext;
import org.modelio.metamodel.diagrams.StaticDiagram;
import org.modelio.metamodel.uml.infrastructure.ModelElement;

import kuaba.Module.listeners.ModelChangeHandler;
import kuaba.Module.api.IKuabaPeerModule;
import kuaba.Module.impl.KuabaModule;

public class CIMDiagram extends DiagramBase {
	public CIMDiagram(ModelElement owner, IStyleHandle style, IModuleContext context) throws Exception {
		
        super(context.getModelingSession().getModel().createStaticDiagram("CIM Diagram", owner,
        		context.getModelingSession().getMetamodelExtensions().getStereotype(IKuabaPeerModule.MODULE_NAME, "CIMDiagram",
        				context.getModelioServices().getMetamodelService().getMetamodel().getMClass(StaticDiagram.class))));

        try (IDiagramHandle diagramHandler = context.getModelioServices().getDiagramService().getDiagramHandle(getElement())) {
            diagramHandler.getDiagramNode().setStyle(style);
            diagramHandler.save();
            diagramHandler.close();
        }
        
        IModelChangeHandler modelHandler = (IModelChangeHandler) new ModelChangeHandler();
		KuabaModule.getInstance().getModuleContext().getModelingSession().addModelHandler(modelHandler);
    }
	
	public CIMDiagram(StaticDiagram element) {
        super(element);
    }  
}