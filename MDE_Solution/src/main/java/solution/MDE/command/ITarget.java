package solution.MDE.command;

import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.module.IModule;
import org.modelio.metamodel.uml.statik.Class;

public interface ITarget {
    Class getElementinTarget(IModelingSession session, IModule module, org.modelio.metamodel.uml.statik.Package target, Class element);
}
