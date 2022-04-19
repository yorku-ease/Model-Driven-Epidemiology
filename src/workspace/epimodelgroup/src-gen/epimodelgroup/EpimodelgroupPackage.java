/**
 */
package epimodelgroup;

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
 * @see epimodelgroup.EpimodelgroupFactory
 * @model kind="package"
 * @generated
 */
public interface EpimodelgroupPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "epimodelgroup";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/epimodelgroup";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "epimodelgroup";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EpimodelgroupPackage eINSTANCE = epimodelgroup.impl.EpimodelgroupPackageImpl.init();

	/**
	 * The meta object id for the '{@link epimodelgroup.impl.EpidemicImpl <em>Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodelgroup.impl.EpidemicImpl
	 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getEpidemic()
	 * @generated
	 */
	int EPIDEMIC = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__ID = 0;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__DIMENSION = 1;

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
	 * The meta object id for the '{@link epimodelgroup.impl.IDimensionImpl <em>IDimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodelgroup.impl.IDimensionImpl
	 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getIDimension()
	 * @generated
	 */
	int IDIMENSION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDIMENSION__ID = 0;

	/**
	 * The number of structural features of the '<em>IDimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDIMENSION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IDimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDIMENSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epimodelgroup.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodelgroup.impl.DimensionImpl
	 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__ID = IDIMENSION__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__COMPARTMENT = IDIMENSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__FLOW = IDIMENSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = IDIMENSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = IDIMENSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epimodelgroup.impl.MetaCompartmentImpl <em>Meta Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodelgroup.impl.MetaCompartmentImpl
	 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getMetaCompartment()
	 * @generated
	 */
	int META_COMPARTMENT = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_COMPARTMENT__ID = IDIMENSION__ID;

	/**
	 * The number of structural features of the '<em>Meta Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_COMPARTMENT_FEATURE_COUNT = IDIMENSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Meta Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_COMPARTMENT_OPERATION_COUNT = IDIMENSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epimodelgroup.impl.MetaFlowImpl <em>Meta Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodelgroup.impl.MetaFlowImpl
	 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getMetaFlow()
	 * @generated
	 */
	int META_FLOW = 4;

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
	 * The meta object id for the '{@link epimodelgroup.impl.DimensionRefImpl <em>Dimension Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodelgroup.impl.DimensionRefImpl
	 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getDimensionRef()
	 * @generated
	 */
	int DIMENSION_REF = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_REF__ID = IDIMENSION__ID;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_REF__REFERENCE = IDIMENSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dimension Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_REF_FEATURE_COUNT = IDIMENSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Dimension Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_REF_OPERATION_COUNT = IDIMENSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epimodelgroup.impl.MetaContactImpl <em>Meta Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodelgroup.impl.MetaContactImpl
	 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getMetaContact()
	 * @generated
	 */
	int META_CONTACT = 6;

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
	 * The meta object id for the '{@link epimodelgroup.impl.MetaRateImpl <em>Meta Rate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodelgroup.impl.MetaRateImpl
	 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getMetaRate()
	 * @generated
	 */
	int META_RATE = 7;

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
	 * The meta object id for the '{@link epimodelgroup.impl.MetaBatchImpl <em>Meta Batch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodelgroup.impl.MetaBatchImpl
	 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getMetaBatch()
	 * @generated
	 */
	int META_BATCH = 8;

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
	 * Returns the meta object for class '{@link epimodelgroup.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Epidemic</em>'.
	 * @see epimodelgroup.Epidemic
	 * @generated
	 */
	EClass getEpidemic();

	/**
	 * Returns the meta object for the containment reference list '{@link epimodelgroup.Epidemic#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dimension</em>'.
	 * @see epimodelgroup.Epidemic#getDimension()
	 * @see #getEpidemic()
	 * @generated
	 */
	EReference getEpidemic_Dimension();

	/**
	 * Returns the meta object for the attribute '{@link epimodelgroup.Epidemic#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epimodelgroup.Epidemic#getId()
	 * @see #getEpidemic()
	 * @generated
	 */
	EAttribute getEpidemic_Id();

	/**
	 * Returns the meta object for class '{@link epimodelgroup.IDimension <em>IDimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IDimension</em>'.
	 * @see epimodelgroup.IDimension
	 * @generated
	 */
	EClass getIDimension();

	/**
	 * Returns the meta object for the attribute '{@link epimodelgroup.IDimension#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epimodelgroup.IDimension#getId()
	 * @see #getIDimension()
	 * @generated
	 */
	EAttribute getIDimension_Id();

	/**
	 * Returns the meta object for class '{@link epimodelgroup.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see epimodelgroup.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the containment reference list '{@link epimodelgroup.Dimension#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see epimodelgroup.Dimension#getCompartment()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Compartment();

	/**
	 * Returns the meta object for the containment reference list '{@link epimodelgroup.Dimension#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see epimodelgroup.Dimension#getFlow()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Flow();

	/**
	 * Returns the meta object for class '{@link epimodelgroup.MetaCompartment <em>Meta Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Compartment</em>'.
	 * @see epimodelgroup.MetaCompartment
	 * @generated
	 */
	EClass getMetaCompartment();

	/**
	 * Returns the meta object for class '{@link epimodelgroup.MetaFlow <em>Meta Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Flow</em>'.
	 * @see epimodelgroup.MetaFlow
	 * @generated
	 */
	EClass getMetaFlow();

	/**
	 * Returns the meta object for the reference '{@link epimodelgroup.MetaFlow#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see epimodelgroup.MetaFlow#getFrom()
	 * @see #getMetaFlow()
	 * @generated
	 */
	EReference getMetaFlow_From();

	/**
	 * Returns the meta object for the reference '{@link epimodelgroup.MetaFlow#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see epimodelgroup.MetaFlow#getTo()
	 * @see #getMetaFlow()
	 * @generated
	 */
	EReference getMetaFlow_To();

	/**
	 * Returns the meta object for the attribute '{@link epimodelgroup.MetaFlow#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epimodelgroup.MetaFlow#getId()
	 * @see #getMetaFlow()
	 * @generated
	 */
	EAttribute getMetaFlow_Id();

	/**
	 * Returns the meta object for class '{@link epimodelgroup.DimensionRef <em>Dimension Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension Ref</em>'.
	 * @see epimodelgroup.DimensionRef
	 * @generated
	 */
	EClass getDimensionRef();

	/**
	 * Returns the meta object for the reference '{@link epimodelgroup.DimensionRef#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reference</em>'.
	 * @see epimodelgroup.DimensionRef#getReference()
	 * @see #getDimensionRef()
	 * @generated
	 */
	EReference getDimensionRef_Reference();

	/**
	 * Returns the meta object for class '{@link epimodelgroup.MetaContact <em>Meta Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Contact</em>'.
	 * @see epimodelgroup.MetaContact
	 * @generated
	 */
	EClass getMetaContact();

	/**
	 * Returns the meta object for the reference '{@link epimodelgroup.MetaContact#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contact</em>'.
	 * @see epimodelgroup.MetaContact#getContact()
	 * @see #getMetaContact()
	 * @generated
	 */
	EReference getMetaContact_Contact();

	/**
	 * Returns the meta object for class '{@link epimodelgroup.MetaRate <em>Meta Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Rate</em>'.
	 * @see epimodelgroup.MetaRate
	 * @generated
	 */
	EClass getMetaRate();

	/**
	 * Returns the meta object for class '{@link epimodelgroup.MetaBatch <em>Meta Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Batch</em>'.
	 * @see epimodelgroup.MetaBatch
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
	EpimodelgroupFactory getEpimodelgroupFactory();

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
		 * The meta object literal for the '{@link epimodelgroup.impl.EpidemicImpl <em>Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodelgroup.impl.EpidemicImpl
		 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getEpidemic()
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
		 * The meta object literal for the '{@link epimodelgroup.impl.IDimensionImpl <em>IDimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodelgroup.impl.IDimensionImpl
		 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getIDimension()
		 * @generated
		 */
		EClass IDIMENSION = eINSTANCE.getIDimension();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDIMENSION__ID = eINSTANCE.getIDimension_Id();

		/**
		 * The meta object literal for the '{@link epimodelgroup.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodelgroup.impl.DimensionImpl
		 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getDimension()
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
		 * The meta object literal for the '{@link epimodelgroup.impl.MetaCompartmentImpl <em>Meta Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodelgroup.impl.MetaCompartmentImpl
		 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getMetaCompartment()
		 * @generated
		 */
		EClass META_COMPARTMENT = eINSTANCE.getMetaCompartment();

		/**
		 * The meta object literal for the '{@link epimodelgroup.impl.MetaFlowImpl <em>Meta Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodelgroup.impl.MetaFlowImpl
		 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getMetaFlow()
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
		 * The meta object literal for the '{@link epimodelgroup.impl.DimensionRefImpl <em>Dimension Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodelgroup.impl.DimensionRefImpl
		 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getDimensionRef()
		 * @generated
		 */
		EClass DIMENSION_REF = eINSTANCE.getDimensionRef();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION_REF__REFERENCE = eINSTANCE.getDimensionRef_Reference();

		/**
		 * The meta object literal for the '{@link epimodelgroup.impl.MetaContactImpl <em>Meta Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodelgroup.impl.MetaContactImpl
		 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getMetaContact()
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
		 * The meta object literal for the '{@link epimodelgroup.impl.MetaRateImpl <em>Meta Rate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodelgroup.impl.MetaRateImpl
		 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getMetaRate()
		 * @generated
		 */
		EClass META_RATE = eINSTANCE.getMetaRate();

		/**
		 * The meta object literal for the '{@link epimodelgroup.impl.MetaBatchImpl <em>Meta Batch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodelgroup.impl.MetaBatchImpl
		 * @see epimodelgroup.impl.EpimodelgroupPackageImpl#getMetaBatch()
		 * @generated
		 */
		EClass META_BATCH = eINSTANCE.getMetaBatch();

	}

} //EpimodelgroupPackage
