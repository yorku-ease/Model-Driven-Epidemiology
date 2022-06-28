/**
 */
package compartmentGroup;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see compartmentGroup.CompartmentGroupPackage
 * @generated
 */
public interface CompartmentGroupFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CompartmentGroupFactory eINSTANCE = compartmentGroup.impl.CompartmentGroupFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Group</em>'.
	 * @generated
	 */
	Group createGroup();

	/**
	 * Returns a new object of class '<em>Group Sources</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Group Sources</em>'.
	 * @generated
	 */
	GroupSources createGroupSources();

	/**
	 * Returns a new object of class '<em>Group Sinks</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Group Sinks</em>'.
	 * @generated
	 */
	GroupSinks createGroupSinks();

	/**
	 * Returns a new object of class '<em>End</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>End</em>'.
	 * @generated
	 */
	End createEnd();

	/**
	 * Returns a new object of class '<em>Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Link</em>'.
	 * @generated
	 */
	Link createLink();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CompartmentGroupPackage getCompartmentGroupPackage();

} //CompartmentGroupFactory
