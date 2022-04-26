/**
 */
package transformedEpidemic.impl;

import epidemicDimension.EpidemicDimensionPackage;
import epimodel.EpimodelPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import transformedEpidemic.TransformedEpidemic;
import transformedEpidemic.TransformedEpidemicFactory;
import transformedEpidemic.TransformedEpidemicPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TransformedEpidemicPackageImpl extends EPackageImpl implements TransformedEpidemicPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transformedEpidemicEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see transformedEpidemic.TransformedEpidemicPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TransformedEpidemicPackageImpl() {
		super(eNS_URI, TransformedEpidemicFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link TransformedEpidemicPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TransformedEpidemicPackage init() {
		if (isInited)
			return (TransformedEpidemicPackage) EPackage.Registry.INSTANCE
					.getEPackage(TransformedEpidemicPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTransformedEpidemicPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TransformedEpidemicPackageImpl theTransformedEpidemicPackage = registeredTransformedEpidemicPackage instanceof TransformedEpidemicPackageImpl
				? (TransformedEpidemicPackageImpl) registeredTransformedEpidemicPackage
				: new TransformedEpidemicPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EpidemicDimensionPackage.eINSTANCE.eClass();
		EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTransformedEpidemicPackage.createPackageContents();

		// Initialize created meta-data
		theTransformedEpidemicPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTransformedEpidemicPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TransformedEpidemicPackage.eNS_URI, theTransformedEpidemicPackage);
		return theTransformedEpidemicPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTransformedEpidemic() {
		return transformedEpidemicEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TransformedEpidemicFactory getTransformedEpidemicFactory() {
		return (TransformedEpidemicFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		transformedEpidemicEClass = createEClass(TRANSFORMED_EPIDEMIC);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EpidemicDimensionPackage theEpidemicDimensionPackage = (EpidemicDimensionPackage) EPackage.Registry.INSTANCE
				.getEPackage(EpidemicDimensionPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		transformedEpidemicEClass.getESuperTypes().add(theEpidemicDimensionPackage.getDimension());

		// Initialize classes, features, and operations; add parameters
		initEClass(transformedEpidemicEClass, TransformedEpidemic.class, "TransformedEpidemic", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //TransformedEpidemicPackageImpl
