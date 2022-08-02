/**
 */
package compartmentGroup;

import epimodel.EpimodelPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see compartmentGroup.CompartmentGroupFactory
 * @model kind="package"
 * @generated
 */
public interface CompartmentGroupPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "compartmentGroup";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/compartmentGroup";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "compartmentGroup";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CompartmentGroupPackage eINSTANCE = compartmentGroup.impl.CompartmentGroupPackageImpl.init();

	/**
	 * The meta object id for the '{@link compartmentGroup.impl.GroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see compartmentGroup.impl.GroupImpl
	 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__LABEL = EpimodelPackage.COMPARTMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__COMPARTMENT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__FLOW = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Group Sinks</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__GROUP_SINKS = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Group Sources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__GROUP_SOURCES = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_FEATURE_COUNT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_OPERATION_COUNT = EpimodelPackage.COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link compartmentGroup.impl.EndImpl <em>End</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see compartmentGroup.impl.EndImpl
	 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getEnd()
	 * @generated
	 */
	int END = 3;

	/**
	 * The feature id for the '<em><b>Link</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END__LINK = 0;

	/**
	 * The number of structural features of the '<em>End</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>End</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link compartmentGroup.impl.GroupSourcesImpl <em>Group Sources</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see compartmentGroup.impl.GroupSourcesImpl
	 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getGroupSources()
	 * @generated
	 */
	int GROUP_SOURCES = 1;

	/**
	 * The feature id for the '<em><b>Link</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_SOURCES__LINK = END__LINK;

	/**
	 * The number of structural features of the '<em>Group Sources</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_SOURCES_FEATURE_COUNT = END_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Group Sources</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_SOURCES_OPERATION_COUNT = END_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link compartmentGroup.impl.GroupSinksImpl <em>Group Sinks</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see compartmentGroup.impl.GroupSinksImpl
	 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getGroupSinks()
	 * @generated
	 */
	int GROUP_SINKS = 2;

	/**
	 * The feature id for the '<em><b>Link</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_SINKS__LINK = END__LINK;

	/**
	 * The number of structural features of the '<em>Group Sinks</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_SINKS_FEATURE_COUNT = END_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Group Sinks</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_SINKS_OPERATION_COUNT = END_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link compartmentGroup.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see compartmentGroup.impl.LinkImpl
	 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 4;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__COMPARTMENT = 0;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link compartmentGroup.impl.GroupEpidemicImpl <em>Group Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see compartmentGroup.impl.GroupEpidemicImpl
	 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getGroupEpidemic()
	 * @generated
	 */
	int GROUP_EPIDEMIC = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_EPIDEMIC__ID = EpimodelPackage.EPIDEMIC__ID;

	/**
	 * The feature id for the '<em><b>Group Sinks</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_EPIDEMIC__GROUP_SINKS = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Group Sources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_EPIDEMIC__GROUP_SOURCES = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_EPIDEMIC__FLOW = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_EPIDEMIC__COMPARTMENT = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Group Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_EPIDEMIC_FEATURE_COUNT = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Group Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_EPIDEMIC_OPERATION_COUNT = EpimodelPackage.EPIDEMIC_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link compartmentGroup.Group <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see compartmentGroup.Group
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link compartmentGroup.Group#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see compartmentGroup.Group#getCompartment()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Compartment();

	/**
	 * Returns the meta object for the containment reference list '{@link compartmentGroup.Group#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see compartmentGroup.Group#getFlow()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Flow();

	/**
	 * Returns the meta object for the containment reference '{@link compartmentGroup.Group#getGroupSinks <em>Group Sinks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Group Sinks</em>'.
	 * @see compartmentGroup.Group#getGroupSinks()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_GroupSinks();

	/**
	 * Returns the meta object for the containment reference '{@link compartmentGroup.Group#getGroupSources <em>Group Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Group Sources</em>'.
	 * @see compartmentGroup.Group#getGroupSources()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_GroupSources();

	/**
	 * Returns the meta object for class '{@link compartmentGroup.GroupSources <em>Group Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group Sources</em>'.
	 * @see compartmentGroup.GroupSources
	 * @generated
	 */
	EClass getGroupSources();

	/**
	 * Returns the meta object for class '{@link compartmentGroup.GroupSinks <em>Group Sinks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group Sinks</em>'.
	 * @see compartmentGroup.GroupSinks
	 * @generated
	 */
	EClass getGroupSinks();

	/**
	 * Returns the meta object for class '{@link compartmentGroup.End <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>End</em>'.
	 * @see compartmentGroup.End
	 * @generated
	 */
	EClass getEnd();

	/**
	 * Returns the meta object for the containment reference list '{@link compartmentGroup.End#getLink <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Link</em>'.
	 * @see compartmentGroup.End#getLink()
	 * @see #getEnd()
	 * @generated
	 */
	EReference getEnd_Link();

	/**
	 * Returns the meta object for class '{@link compartmentGroup.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see compartmentGroup.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '{@link compartmentGroup.Link#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Compartment</em>'.
	 * @see compartmentGroup.Link#getCompartment()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Compartment();

	/**
	 * Returns the meta object for class '{@link compartmentGroup.GroupEpidemic <em>Group Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group Epidemic</em>'.
	 * @see compartmentGroup.GroupEpidemic
	 * @generated
	 */
	EClass getGroupEpidemic();

	/**
	 * Returns the meta object for the containment reference '{@link compartmentGroup.GroupEpidemic#getGroupSinks <em>Group Sinks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Group Sinks</em>'.
	 * @see compartmentGroup.GroupEpidemic#getGroupSinks()
	 * @see #getGroupEpidemic()
	 * @generated
	 */
	EReference getGroupEpidemic_GroupSinks();

	/**
	 * Returns the meta object for the containment reference '{@link compartmentGroup.GroupEpidemic#getGroupSources <em>Group Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Group Sources</em>'.
	 * @see compartmentGroup.GroupEpidemic#getGroupSources()
	 * @see #getGroupEpidemic()
	 * @generated
	 */
	EReference getGroupEpidemic_GroupSources();

	/**
	 * Returns the meta object for the containment reference list '{@link compartmentGroup.GroupEpidemic#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see compartmentGroup.GroupEpidemic#getFlow()
	 * @see #getGroupEpidemic()
	 * @generated
	 */
	EReference getGroupEpidemic_Flow();

	/**
	 * Returns the meta object for the containment reference list '{@link compartmentGroup.GroupEpidemic#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see compartmentGroup.GroupEpidemic#getCompartment()
	 * @see #getGroupEpidemic()
	 * @generated
	 */
	EReference getGroupEpidemic_Compartment();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CompartmentGroupFactory getCompartmentGroupFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link compartmentGroup.impl.GroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see compartmentGroup.impl.GroupImpl
		 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getGroup()
		 * @generated
		 */
		EClass GROUP = eINSTANCE.getGroup();

		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP__COMPARTMENT = eINSTANCE.getGroup_Compartment();

		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP__FLOW = eINSTANCE.getGroup_Flow();

		/**
		 * The meta object literal for the '<em><b>Group Sinks</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP__GROUP_SINKS = eINSTANCE.getGroup_GroupSinks();

		/**
		 * The meta object literal for the '<em><b>Group Sources</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP__GROUP_SOURCES = eINSTANCE.getGroup_GroupSources();

		/**
		 * The meta object literal for the '{@link compartmentGroup.impl.GroupSourcesImpl <em>Group Sources</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see compartmentGroup.impl.GroupSourcesImpl
		 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getGroupSources()
		 * @generated
		 */
		EClass GROUP_SOURCES = eINSTANCE.getGroupSources();

		/**
		 * The meta object literal for the '{@link compartmentGroup.impl.GroupSinksImpl <em>Group Sinks</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see compartmentGroup.impl.GroupSinksImpl
		 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getGroupSinks()
		 * @generated
		 */
		EClass GROUP_SINKS = eINSTANCE.getGroupSinks();

		/**
		 * The meta object literal for the '{@link compartmentGroup.impl.EndImpl <em>End</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see compartmentGroup.impl.EndImpl
		 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getEnd()
		 * @generated
		 */
		EClass END = eINSTANCE.getEnd();

		/**
		 * The meta object literal for the '<em><b>Link</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference END__LINK = eINSTANCE.getEnd_Link();

		/**
		 * The meta object literal for the '{@link compartmentGroup.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see compartmentGroup.impl.LinkImpl
		 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__COMPARTMENT = eINSTANCE.getLink_Compartment();

		/**
		 * The meta object literal for the '{@link compartmentGroup.impl.GroupEpidemicImpl <em>Group Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see compartmentGroup.impl.GroupEpidemicImpl
		 * @see compartmentGroup.impl.CompartmentGroupPackageImpl#getGroupEpidemic()
		 * @generated
		 */
		EClass GROUP_EPIDEMIC = eINSTANCE.getGroupEpidemic();

		/**
		 * The meta object literal for the '<em><b>Group Sinks</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP_EPIDEMIC__GROUP_SINKS = eINSTANCE.getGroupEpidemic_GroupSinks();

		/**
		 * The meta object literal for the '<em><b>Group Sources</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP_EPIDEMIC__GROUP_SOURCES = eINSTANCE.getGroupEpidemic_GroupSources();

		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP_EPIDEMIC__FLOW = eINSTANCE.getGroupEpidemic_Flow();

		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP_EPIDEMIC__COMPARTMENT = eINSTANCE.getGroupEpidemic_Compartment();

	}

} //CompartmentGroupPackage
