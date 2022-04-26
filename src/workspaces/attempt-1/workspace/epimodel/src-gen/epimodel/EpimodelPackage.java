/**
 */
package epimodel;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see epimodel.EpimodelFactory
 * @model kind="package"
 * @generated
 */
public interface EpimodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "epimodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/epimodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "epimodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EpimodelPackage eINSTANCE = epimodel.impl.EpimodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link epimodel.impl.EpidemicImpl <em>Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.EpidemicImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getEpidemic()
	 * @generated
	 */
	int EPIDEMIC = 0;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__DIMENSION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__ID = 1;

	/**
	 * The number of structural features of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.DimensionImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 1;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__COMPARTMENT = 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__FLOW = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__ID = 2;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.MetaCompartmentImpl <em>Meta Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.MetaCompartmentImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getMetaCompartment()
	 * @generated
	 */
	int META_COMPARTMENT = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_COMPARTMENT__ID = 0;

	/**
	 * The number of structural features of the '<em>Meta Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_COMPARTMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Meta Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_COMPARTMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.MetaFlowImpl <em>Meta Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.MetaFlowImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getMetaFlow()
	 * @generated
	 */
	int META_FLOW = 3;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_FLOW__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_FLOW__TO = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_FLOW__ID = 2;

	/**
	 * The number of structural features of the '<em>Meta Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_FLOW_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Meta Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_FLOW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.MetaContactImpl <em>Meta Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.MetaContactImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getMetaContact()
	 * @generated
	 */
	int META_CONTACT = 4;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CONTACT__FROM = META_FLOW__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CONTACT__TO = META_FLOW__TO;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CONTACT__ID = META_FLOW__ID;

	/**
	 * The feature id for the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CONTACT__CONTACT = META_FLOW_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Meta Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CONTACT_FEATURE_COUNT = META_FLOW_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Meta Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CONTACT_OPERATION_COUNT = META_FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.MetaRateImpl <em>Meta Rate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.MetaRateImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getMetaRate()
	 * @generated
	 */
	int META_RATE = 5;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_RATE__FROM = META_FLOW__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_RATE__TO = META_FLOW__TO;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_RATE__ID = META_FLOW__ID;

	/**
	 * The number of structural features of the '<em>Meta Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_RATE_FEATURE_COUNT = META_FLOW_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Meta Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_RATE_OPERATION_COUNT = META_FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.MetaBatchImpl <em>Meta Batch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.MetaBatchImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getMetaBatch()
	 * @generated
	 */
	int META_BATCH = 6;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_BATCH__FROM = META_FLOW__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_BATCH__TO = META_FLOW__TO;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_BATCH__ID = META_FLOW__ID;

	/**
	 * The number of structural features of the '<em>Meta Batch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_BATCH_FEATURE_COUNT = META_FLOW_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Meta Batch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_BATCH_OPERATION_COUNT = META_FLOW_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link epimodel.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Epidemic</em>'.
	 * @see epimodel.Epidemic
	 * @generated
	 */
	EClass getEpidemic();

	/**
	 * Returns the meta object for the containment reference list '{@link epimodel.Epidemic#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dimension</em>'.
	 * @see epimodel.Epidemic#getDimension()
	 * @see #getEpidemic()
	 * @generated
	 */
	EReference getEpidemic_Dimension();

	/**
	 * Returns the meta object for the attribute '{@link epimodel.Epidemic#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epimodel.Epidemic#getId()
	 * @see #getEpidemic()
	 * @generated
	 */
	EAttribute getEpidemic_Id();

	/**
	 * Returns the meta object for class '{@link epimodel.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see epimodel.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the containment reference list '{@link epimodel.Dimension#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see epimodel.Dimension#getCompartment()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Compartment();

	/**
	 * Returns the meta object for the containment reference list '{@link epimodel.Dimension#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see epimodel.Dimension#getFlow()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Flow();

	/**
	 * Returns the meta object for the attribute '{@link epimodel.Dimension#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epimodel.Dimension#getId()
	 * @see #getDimension()
	 * @generated
	 */
	EAttribute getDimension_Id();

	/**
	 * Returns the meta object for class '{@link epimodel.MetaCompartment <em>Meta Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Compartment</em>'.
	 * @see epimodel.MetaCompartment
	 * @generated
	 */
	EClass getMetaCompartment();

	/**
	 * Returns the meta object for the attribute '{@link epimodel.MetaCompartment#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epimodel.MetaCompartment#getId()
	 * @see #getMetaCompartment()
	 * @generated
	 */
	EAttribute getMetaCompartment_Id();

	/**
	 * Returns the meta object for class '{@link epimodel.MetaFlow <em>Meta Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Flow</em>'.
	 * @see epimodel.MetaFlow
	 * @generated
	 */
	EClass getMetaFlow();

	/**
	 * Returns the meta object for the reference '{@link epimodel.MetaFlow#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see epimodel.MetaFlow#getFrom()
	 * @see #getMetaFlow()
	 * @generated
	 */
	EReference getMetaFlow_From();

	/**
	 * Returns the meta object for the reference '{@link epimodel.MetaFlow#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see epimodel.MetaFlow#getTo()
	 * @see #getMetaFlow()
	 * @generated
	 */
	EReference getMetaFlow_To();

	/**
	 * Returns the meta object for the attribute '{@link epimodel.MetaFlow#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epimodel.MetaFlow#getId()
	 * @see #getMetaFlow()
	 * @generated
	 */
	EAttribute getMetaFlow_Id();

	/**
	 * Returns the meta object for class '{@link epimodel.MetaContact <em>Meta Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Contact</em>'.
	 * @see epimodel.MetaContact
	 * @generated
	 */
	EClass getMetaContact();

	/**
	 * Returns the meta object for the reference '{@link epimodel.MetaContact#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contact</em>'.
	 * @see epimodel.MetaContact#getContact()
	 * @see #getMetaContact()
	 * @generated
	 */
	EReference getMetaContact_Contact();

	/**
	 * Returns the meta object for class '{@link epimodel.MetaRate <em>Meta Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Rate</em>'.
	 * @see epimodel.MetaRate
	 * @generated
	 */
	EClass getMetaRate();

	/**
	 * Returns the meta object for class '{@link epimodel.MetaBatch <em>Meta Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Batch</em>'.
	 * @see epimodel.MetaBatch
	 * @generated
	 */
	EClass getMetaBatch();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EpimodelFactory getEpimodelFactory();

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
		 * The meta object literal for the '{@link epimodel.impl.EpidemicImpl <em>Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.EpidemicImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getEpidemic()
		 * @generated
		 */
		EClass EPIDEMIC = eINSTANCE.getEpidemic();

		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EPIDEMIC__DIMENSION = eINSTANCE.getEpidemic_Dimension();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPIDEMIC__ID = eINSTANCE.getEpidemic_Id();

		/**
		 * The meta object literal for the '{@link epimodel.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.DimensionImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getDimension()
		 * @generated
		 */
		EClass DIMENSION = eINSTANCE.getDimension();

		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__COMPARTMENT = eINSTANCE.getDimension_Compartment();

		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__FLOW = eINSTANCE.getDimension_Flow();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIMENSION__ID = eINSTANCE.getDimension_Id();

		/**
		 * The meta object literal for the '{@link epimodel.impl.MetaCompartmentImpl <em>Meta Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.MetaCompartmentImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getMetaCompartment()
		 * @generated
		 */
		EClass META_COMPARTMENT = eINSTANCE.getMetaCompartment();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute META_COMPARTMENT__ID = eINSTANCE.getMetaCompartment_Id();

		/**
		 * The meta object literal for the '{@link epimodel.impl.MetaFlowImpl <em>Meta Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.MetaFlowImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getMetaFlow()
		 * @generated
		 */
		EClass META_FLOW = eINSTANCE.getMetaFlow();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_FLOW__FROM = eINSTANCE.getMetaFlow_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_FLOW__TO = eINSTANCE.getMetaFlow_To();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute META_FLOW__ID = eINSTANCE.getMetaFlow_Id();

		/**
		 * The meta object literal for the '{@link epimodel.impl.MetaContactImpl <em>Meta Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.MetaContactImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getMetaContact()
		 * @generated
		 */
		EClass META_CONTACT = eINSTANCE.getMetaContact();

		/**
		 * The meta object literal for the '<em><b>Contact</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_CONTACT__CONTACT = eINSTANCE.getMetaContact_Contact();

		/**
		 * The meta object literal for the '{@link epimodel.impl.MetaRateImpl <em>Meta Rate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.MetaRateImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getMetaRate()
		 * @generated
		 */
		EClass META_RATE = eINSTANCE.getMetaRate();

		/**
		 * The meta object literal for the '{@link epimodel.impl.MetaBatchImpl <em>Meta Batch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.MetaBatchImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getMetaBatch()
		 * @generated
		 */
		EClass META_BATCH = eINSTANCE.getMetaBatch();

	}

} //EpimodelPackage
