package kuaba.Module.diagram;

import org.modelio.metamodel.diagrams.AbstractDiagram;
import org.modelio.metamodel.diagrams.StaticDiagram;
import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.metamodel.uml.infrastructure.Stereotype;
import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.module.context.IModuleContext;

import kuaba.Module.impl.KuabaModule;

public class DiagramBase {

	protected AbstractDiagram element;

	public DiagramBase(ModelElement owner) {
		try {
			IModuleContext context = KuabaModule.getInstance().getModuleContext();
			IModelingSession session = context.getModelingSession(); 
			Stereotype stereotype = session.getMetamodelExtensions().getStereotype("KuabaModule","KuabaDiagramBase", context.getModelioServices().getMetamodelService().getMetamodel().getMClass(StaticDiagram.class));
			
			this.element = session.getModel().createStaticDiagram("DiagramBase", owner, stereotype);	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DiagramBase(AbstractDiagram element) {
		this.element = element;
	}

	public AbstractDiagram getElement() {
		return this.element;
	}
}