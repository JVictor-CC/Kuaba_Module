package solution.MDE.command;

import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.module.IModule;
import org.modelio.metamodel.uml.statik.Classifier;
import org.modelio.vcore.smkernel.mapi.MObject;

public interface IOperations {
    void processOperations(IModelingSession session, IModule module, Classifier targetClassifier, MObject sourceElement);
}
