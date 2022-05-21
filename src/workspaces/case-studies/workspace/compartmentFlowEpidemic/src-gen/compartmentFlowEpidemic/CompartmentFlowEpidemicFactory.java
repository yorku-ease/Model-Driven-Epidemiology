/**
 */
package compartmentFlowEpidemic;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see compartmentFlowEpidemic.CompartmentFlowEpidemicPackage
 * @generated
 */
public interface CompartmentFlowEpidemicFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CompartmentFlowEpidemicFactory eINSTANCE = compartmentFlowEpidemic.impl.CompartmentFlowEpidemicFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Compartment Flow Epidemic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compartment Flow Epidemic</em>'.
	 * @generated
	 */
	CompartmentFlowEpidemic createCompartmentFlowEpidemic();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CompartmentFlowEpidemicPackage getCompartmentFlowEpidemicPackage();

} //CompartmentFlowEpidemicFactory
