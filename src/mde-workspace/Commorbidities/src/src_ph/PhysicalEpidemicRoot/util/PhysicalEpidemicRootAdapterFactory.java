/**
 */
package src_ph.PhysicalEpidemicRoot.util;

import src_ph.PhysicalEpidemicRoot.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage
 * @generated
 */
public class PhysicalEpidemicRootAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PhysicalEpidemicRootPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalEpidemicRootAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PhysicalEpidemicRootPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhysicalEpidemicRootSwitch<Adapter> modelSwitch =
		new PhysicalEpidemicRootSwitch<Adapter>() {
			@Override
			public Adapter casePhysicalEpidemic(PhysicalEpidemic object) {
				return createPhysicalEpidemicAdapter();
			}
			@Override
			public Adapter caseLabel(Label object) {
				return createLabelAdapter();
			}
			@Override
			public Adapter caseEquationTemplate(EquationTemplate object) {
				return createEquationTemplateAdapter();
			}
			@Override
			public Adapter casePhysicalCompartment(PhysicalCompartment object) {
				return createPhysicalCompartmentAdapter();
			}
			@Override
			public Adapter casePhysicalFlow(PhysicalFlow object) {
				return createPhysicalFlowAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link PhysicalEpidemicRoot.PhysicalEpidemic <em>Physical Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see PhysicalEpidemicRoot.PhysicalEpidemic
	 * @generated
	 */
	public Adapter createPhysicalEpidemicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link PhysicalEpidemicRoot.Label <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see PhysicalEpidemicRoot.Label
	 * @generated
	 */
	public Adapter createLabelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link PhysicalEpidemicRoot.EquationTemplate <em>Equation Template</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see PhysicalEpidemicRoot.EquationTemplate
	 * @generated
	 */
	public Adapter createEquationTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link PhysicalEpidemicRoot.PhysicalCompartment <em>Physical Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see PhysicalEpidemicRoot.PhysicalCompartment
	 * @generated
	 */
	public Adapter createPhysicalCompartmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link PhysicalEpidemicRoot.PhysicalFlow <em>Physical Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see PhysicalEpidemicRoot.PhysicalFlow
	 * @generated
	 */
	public Adapter createPhysicalFlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //PhysicalEpidemicRootAdapterFactory
