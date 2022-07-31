/**
 */
package epi_merged;

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
 * @see epi_merged.Epi_mergedFactory
 * @model kind="package"
 * @generated
 */
public interface Epi_mergedPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "epi_merged";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/epi_merged";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "epi_merged";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Epi_mergedPackage eINSTANCE = epi_merged.impl.Epi_mergedPackageImpl.init();

	/**
	 * The meta object id for the '{@link epi_merged.impl.EpidemicImpl <em>Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epi_merged.impl.EpidemicImpl
	 * @see epi_merged.impl.Epi_mergedPackageImpl#getEpidemic()
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
	 * The number of structural features of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epi_merged.impl.ProductEpidemicImpl <em>Product Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epi_merged.impl.ProductEpidemicImpl
	 * @see epi_merged.impl.Epi_mergedPackageImpl#getProductEpidemic()
	 * @generated
	 */
	int PRODUCT_EPIDEMIC = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_EPIDEMIC__ID = EPIDEMIC__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_EPIDEMIC__COMPARTMENT = EPIDEMIC_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Product Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_EPIDEMIC_FEATURE_COUNT = EPIDEMIC_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Product Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_EPIDEMIC_OPERATION_COUNT = EPIDEMIC_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epi_merged.impl.CompartmentImpl <em>Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epi_merged.impl.CompartmentImpl
	 * @see epi_merged.impl.Epi_mergedPackageImpl#getCompartment()
	 * @generated
	 */
	int COMPARTMENT = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__ID = 0;

	/**
	 * The number of structural features of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epi_merged.impl.FlowImpl <em>Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epi_merged.impl.FlowImpl
	 * @see epi_merged.impl.Epi_mergedPackageImpl#getFlow()
	 * @generated
	 */
	int FLOW = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__ID = 0;

	/**
	 * The number of structural features of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epi_merged.impl.GroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epi_merged.impl.GroupImpl
	 * @see epi_merged.impl.Epi_mergedPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__ID = COMPARTMENT__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__COMPARTMENT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__FLOW = COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epi_merged.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epi_merged.impl.ProductImpl
	 * @see epi_merged.impl.Epi_mergedPackageImpl#getProduct()
	 * @generated
	 */
	int PRODUCT = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__ID = COMPARTMENT__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__COMPARTMENT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epi_merged.impl.RateImpl <em>Rate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epi_merged.impl.RateImpl
	 * @see epi_merged.impl.Epi_mergedPackageImpl#getRate()
	 * @generated
	 */
	int RATE = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__ID = FLOW__ID;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__FROM = FLOW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__TO = FLOW_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FEATURE_COUNT = FLOW_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_OPERATION_COUNT = FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epi_merged.impl.ContactImpl <em>Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epi_merged.impl.ContactImpl
	 * @see epi_merged.impl.Epi_mergedPackageImpl#getContact()
	 * @generated
	 */
	int CONTACT = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__ID = FLOW__ID;

	/**
	 * The feature id for the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__CONTACT = FLOW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__FROM = FLOW_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__TO = FLOW_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FEATURE_COUNT = FLOW_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_OPERATION_COUNT = FLOW_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link epi_merged.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Epidemic</em>'.
	 * @see epi_merged.Epidemic
	 * @generated
	 */
	EClass getEpidemic();

	/**
	 * Returns the meta object for the attribute '{@link epi_merged.Epidemic#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epi_merged.Epidemic#getId()
	 * @see #getEpidemic()
	 * @generated
	 */
	EAttribute getEpidemic_Id();

	/**
	 * Returns the meta object for class '{@link epi_merged.ProductEpidemic <em>Product Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product Epidemic</em>'.
	 * @see epi_merged.ProductEpidemic
	 * @generated
	 */
	EClass getProductEpidemic();

	/**
	 * Returns the meta object for the containment reference list '{@link epi_merged.ProductEpidemic#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see epi_merged.ProductEpidemic#getCompartment()
	 * @see #getProductEpidemic()
	 * @generated
	 */
	EReference getProductEpidemic_Compartment();

	/**
	 * Returns the meta object for class '{@link epi_merged.Compartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compartment</em>'.
	 * @see epi_merged.Compartment
	 * @generated
	 */
	EClass getCompartment();

	/**
	 * Returns the meta object for the attribute '{@link epi_merged.Compartment#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epi_merged.Compartment#getId()
	 * @see #getCompartment()
	 * @generated
	 */
	EAttribute getCompartment_Id();

	/**
	 * Returns the meta object for class '{@link epi_merged.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see epi_merged.Flow
	 * @generated
	 */
	EClass getFlow();

	/**
	 * Returns the meta object for the attribute '{@link epi_merged.Flow#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epi_merged.Flow#getId()
	 * @see #getFlow()
	 * @generated
	 */
	EAttribute getFlow_Id();

	/**
	 * Returns the meta object for class '{@link epi_merged.Group <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see epi_merged.Group
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link epi_merged.Group#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see epi_merged.Group#getCompartment()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Compartment();

	/**
	 * Returns the meta object for the containment reference list '{@link epi_merged.Group#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see epi_merged.Group#getFlow()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Flow();

	/**
	 * Returns the meta object for class '{@link epi_merged.Product <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product</em>'.
	 * @see epi_merged.Product
	 * @generated
	 */
	EClass getProduct();

	/**
	 * Returns the meta object for the containment reference list '{@link epi_merged.Product#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see epi_merged.Product#getCompartment()
	 * @see #getProduct()
	 * @generated
	 */
	EReference getProduct_Compartment();

	/**
	 * Returns the meta object for class '{@link epi_merged.Rate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rate</em>'.
	 * @see epi_merged.Rate
	 * @generated
	 */
	EClass getRate();

	/**
	 * Returns the meta object for the reference '{@link epi_merged.Rate#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see epi_merged.Rate#getFrom()
	 * @see #getRate()
	 * @generated
	 */
	EReference getRate_From();

	/**
	 * Returns the meta object for the reference '{@link epi_merged.Rate#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see epi_merged.Rate#getTo()
	 * @see #getRate()
	 * @generated
	 */
	EReference getRate_To();

	/**
	 * Returns the meta object for class '{@link epi_merged.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact</em>'.
	 * @see epi_merged.Contact
	 * @generated
	 */
	EClass getContact();

	/**
	 * Returns the meta object for the reference '{@link epi_merged.Contact#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contact</em>'.
	 * @see epi_merged.Contact#getContact()
	 * @see #getContact()
	 * @generated
	 */
	EReference getContact_Contact();

	/**
	 * Returns the meta object for the reference '{@link epi_merged.Contact#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see epi_merged.Contact#getFrom()
	 * @see #getContact()
	 * @generated
	 */
	EReference getContact_From();

	/**
	 * Returns the meta object for the reference '{@link epi_merged.Contact#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see epi_merged.Contact#getTo()
	 * @see #getContact()
	 * @generated
	 */
	EReference getContact_To();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Epi_mergedFactory getEpi_mergedFactory();

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
		 * The meta object literal for the '{@link epi_merged.impl.EpidemicImpl <em>Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epi_merged.impl.EpidemicImpl
		 * @see epi_merged.impl.Epi_mergedPackageImpl#getEpidemic()
		 * @generated
		 */
		EClass EPIDEMIC = eINSTANCE.getEpidemic();
		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPIDEMIC__ID = eINSTANCE.getEpidemic_Id();
		/**
		 * The meta object literal for the '{@link epi_merged.impl.ProductEpidemicImpl <em>Product Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epi_merged.impl.ProductEpidemicImpl
		 * @see epi_merged.impl.Epi_mergedPackageImpl#getProductEpidemic()
		 * @generated
		 */
		EClass PRODUCT_EPIDEMIC = eINSTANCE.getProductEpidemic();
		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT_EPIDEMIC__COMPARTMENT = eINSTANCE.getProductEpidemic_Compartment();
		/**
		 * The meta object literal for the '{@link epi_merged.impl.CompartmentImpl <em>Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epi_merged.impl.CompartmentImpl
		 * @see epi_merged.impl.Epi_mergedPackageImpl#getCompartment()
		 * @generated
		 */
		EClass COMPARTMENT = eINSTANCE.getCompartment();
		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPARTMENT__ID = eINSTANCE.getCompartment_Id();
		/**
		 * The meta object literal for the '{@link epi_merged.impl.FlowImpl <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epi_merged.impl.FlowImpl
		 * @see epi_merged.impl.Epi_mergedPackageImpl#getFlow()
		 * @generated
		 */
		EClass FLOW = eINSTANCE.getFlow();
		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW__ID = eINSTANCE.getFlow_Id();
		/**
		 * The meta object literal for the '{@link epi_merged.impl.GroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epi_merged.impl.GroupImpl
		 * @see epi_merged.impl.Epi_mergedPackageImpl#getGroup()
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
		 * The meta object literal for the '{@link epi_merged.impl.ProductImpl <em>Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epi_merged.impl.ProductImpl
		 * @see epi_merged.impl.Epi_mergedPackageImpl#getProduct()
		 * @generated
		 */
		EClass PRODUCT = eINSTANCE.getProduct();
		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT__COMPARTMENT = eINSTANCE.getProduct_Compartment();
		/**
		 * The meta object literal for the '{@link epi_merged.impl.RateImpl <em>Rate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epi_merged.impl.RateImpl
		 * @see epi_merged.impl.Epi_mergedPackageImpl#getRate()
		 * @generated
		 */
		EClass RATE = eINSTANCE.getRate();
		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RATE__FROM = eINSTANCE.getRate_From();
		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RATE__TO = eINSTANCE.getRate_To();
		/**
		 * The meta object literal for the '{@link epi_merged.impl.ContactImpl <em>Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epi_merged.impl.ContactImpl
		 * @see epi_merged.impl.Epi_mergedPackageImpl#getContact()
		 * @generated
		 */
		EClass CONTACT = eINSTANCE.getContact();
		/**
		 * The meta object literal for the '<em><b>Contact</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTACT__CONTACT = eINSTANCE.getContact_Contact();
		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTACT__FROM = eINSTANCE.getContact_From();
		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTACT__TO = eINSTANCE.getContact_To();

	}

} //Epi_mergedPackage
