package com.Template.impl;

import org.modelio.api.module.AbstractJavaModule;
import org.modelio.api.module.context.IModuleContext;
import org.modelio.api.module.lifecycle.IModuleLifeCycleHandler;
import org.modelio.api.module.parameter.IParameterEditionModel;

/**
 * Implementation of the IModule interface.
 * <br>All Modelio java modules should inherit from this class.
 * 
 */
public class TemplateModuleModule extends AbstractJavaModule {

	private TemplateModulePeerModule peerModule = null;

	private TemplateModuleLifeCycleHandler lifeCycleHandler = null;
	
	private static TemplateModuleModule instance;

	public static TemplateModuleModule getInstance() {
		return instance;
	}

	@Override
	public TemplateModulePeerModule getPeerModule() {
		return this.peerModule;
	}

	/**
	 * Return the lifecycle handler attached to the current module.
	 * <p>
	 * <p>
	 * This handler is used to manage the module lifecycle by declaring the
	 * desired implementation on start, select... methods.
	 */
	@Override
	public IModuleLifeCycleHandler getLifeCycleHandler() {
		return this.lifeCycleHandler;
	}

	/**
	 * Method automatically called just after the creation of the module.
	 * <p>
	 * <p>
	 * The module is automatically instanciated at the beginning of the MDA
	 * lifecycle and constructor implementation is not accessible to the module
	 * developer.
	 * <p>
	 * <p>
	 * The <code>init</code> method allows the developer to execute the desired initialization code at this step. For
     * example, this is the perfect place to register any IViewpoint this module provides.
	 *
	 *
	 * @see org.modelio.api.module.AbstractJavaModule#init()
	 */
	@Override
	public void init() {
		// Add the module initialization code
	    super.init();
	}

    /**
     * Method automatically called just before the disposal of the module.
     * <p>
     * <p>
     * 
     * 
     * The <code>uninit</code> method allows the developer to execute the desired un-initialization code at this step.
     * For example, if IViewpoints have been registered in the {@link #init()} method, this method is the perfect place
     * to remove them.
     * <p>
     * <p>
     * 
     * This method should never be called by the developer because it is already invoked by the tool.
     * 
     * @see org.modelio.api.module.AbstractJavaModule#uninit()
     */
    @Override
    public void uninit() {
        // Add the module un-initialization code
        super.uninit();
    }
    
	/**
	 * Builds a new module.
	 * <p>
	 * <p>
	 * This constructor must not be called by the user. It is automatically
	 * invoked by Modelio when the module is installed, selected or started.
	 * @param moduleContext context of the module, needed to access Modelio features.
	 */
	public TemplateModuleModule(IModuleContext moduleContext) {
		super(moduleContext);

		TemplateModuleModule.instance = this;

		this.lifeCycleHandler = new TemplateModuleLifeCycleHandler(this);
		this.peerModule = new TemplateModulePeerModule(this, moduleContext.getPeerConfiguration());
	}

	/**
	 * @see org.modelio.api.module.AbstractJavaModule#getParametersEditionModel()
	 */
	@Override
	public IParameterEditionModel getParametersEditionModel() {
	    if (this.parameterEditionModel == null) {
	        this.parameterEditionModel = super.getParametersEditionModel();
	    }
		return this.parameterEditionModel;
	}
	
	@Override
    public String getModuleImagePath() {
        return "/res/icons/module_16.png";
    }

}
