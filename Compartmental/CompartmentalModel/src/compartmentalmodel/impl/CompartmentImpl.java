/**
 */
package compartmentalmodel.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import compartmentalmodel.Compartment;
import compartmentalmodel.Flow;
import compartmentalmodel.Product;
import compartmentalmodel.CompartmentalmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compartment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link compartmentalmodel.impl.CompartmentImpl#getPrimaryName <em>Primary Name</em>}</li>
 *   <li>{@link compartmentalmodel.impl.CompartmentImpl#getPopulation <em>Population</em>}</li>
 *   <li>{@link compartmentalmodel.impl.CompartmentImpl#getOutgoingFlows <em>Outgoing Flows</em>}</li>
 *   <li>{@link compartmentalmodel.impl.CompartmentImpl#getSecondaryName <em>Secondary Name</em>}</li>
 *   <li>{@link compartmentalmodel.impl.CompartmentImpl#getProduct <em>Product</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompartmentImpl extends MinimalEObjectImpl.Container implements Compartment {
	/**
	 * The default value of the '{@link #getPrimaryName() <em>Primary Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryName()
	 * @generated
	 * @ordered
	 */
	protected static final String PRIMARY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrimaryName() <em>Primary Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryName()
	 * @generated
	 * @ordered
	 */
	protected String primaryName = PRIMARY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPopulation() <em>Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPopulation()
	 * @generated
	 * @ordered
	 */
	protected static final int POPULATION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPopulation() <em>Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPopulation()
	 * @generated
	 * @ordered
	 */
	protected int population = POPULATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutgoingFlows() <em>Outgoing Flows</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingFlows()
	 * @generated
	 * @ordered
	 */
	protected EList<Flow> outgoingFlows;

	/**
	 * The default value of the '{@link #getSecondaryName() <em>Secondary Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryName()
	 * @generated
	 * @ordered
	 */
	protected static final String SECONDARY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSecondaryName() <em>Secondary Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryName()
	 * @generated
	 * @ordered
	 */
	protected String secondaryName = SECONDARY_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProduct() <em>Product</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProduct()
	 * @generated
	 * @ordered
	 */
	protected Product product;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompartmentalmodelPackage.Literals.COMPARTMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPrimaryName() {
		return primaryName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPrimaryName(String newPrimaryName) {
		String oldPrimaryName = primaryName;
		primaryName = newPrimaryName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentalmodelPackage.COMPARTMENT__PRIMARY_NAME, oldPrimaryName, primaryName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getPopulation() {
		return population;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPopulation(int newPopulation) {
		int oldPopulation = population;
		population = newPopulation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentalmodelPackage.COMPARTMENT__POPULATION, oldPopulation, population));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Flow> getOutgoingFlows() {
		if (outgoingFlows == null) {
			outgoingFlows = new EObjectContainmentEList<Flow>(Flow.class, this, CompartmentalmodelPackage.COMPARTMENT__OUTGOING_FLOWS);
		}
		return outgoingFlows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSecondaryName() {
		return secondaryName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecondaryName(String newSecondaryName) {
		String oldSecondaryName = secondaryName;
		secondaryName = newSecondaryName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentalmodelPackage.COMPARTMENT__SECONDARY_NAME, oldSecondaryName, secondaryName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Product getProduct() {
		if (product != null && product.eIsProxy()) {
			InternalEObject oldProduct = (InternalEObject)product;
			product = (Product)eResolveProxy(oldProduct);
			if (product != oldProduct) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CompartmentalmodelPackage.COMPARTMENT__PRODUCT, oldProduct, product));
			}
		}
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Product basicGetProduct() {
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProduct(Product newProduct) {
		Product oldProduct = product;
		product = newProduct;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentalmodelPackage.COMPARTMENT__PRODUCT, oldProduct, product));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CompartmentalmodelPackage.COMPARTMENT__OUTGOING_FLOWS:
				return ((InternalEList<?>)getOutgoingFlows()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CompartmentalmodelPackage.COMPARTMENT__PRIMARY_NAME:
				return getPrimaryName();
			case CompartmentalmodelPackage.COMPARTMENT__POPULATION:
				return getPopulation();
			case CompartmentalmodelPackage.COMPARTMENT__OUTGOING_FLOWS:
				return getOutgoingFlows();
			case CompartmentalmodelPackage.COMPARTMENT__SECONDARY_NAME:
				return getSecondaryName();
			case CompartmentalmodelPackage.COMPARTMENT__PRODUCT:
				if (resolve) return getProduct();
				return basicGetProduct();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CompartmentalmodelPackage.COMPARTMENT__PRIMARY_NAME:
				setPrimaryName((String)newValue);
				return;
			case CompartmentalmodelPackage.COMPARTMENT__POPULATION:
				setPopulation((Integer)newValue);
				return;
			case CompartmentalmodelPackage.COMPARTMENT__OUTGOING_FLOWS:
				getOutgoingFlows().clear();
				getOutgoingFlows().addAll((Collection<? extends Flow>)newValue);
				return;
			case CompartmentalmodelPackage.COMPARTMENT__SECONDARY_NAME:
				setSecondaryName((String)newValue);
				return;
			case CompartmentalmodelPackage.COMPARTMENT__PRODUCT:
				setProduct((Product)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CompartmentalmodelPackage.COMPARTMENT__PRIMARY_NAME:
				setPrimaryName(PRIMARY_NAME_EDEFAULT);
				return;
			case CompartmentalmodelPackage.COMPARTMENT__POPULATION:
				setPopulation(POPULATION_EDEFAULT);
				return;
			case CompartmentalmodelPackage.COMPARTMENT__OUTGOING_FLOWS:
				getOutgoingFlows().clear();
				return;
			case CompartmentalmodelPackage.COMPARTMENT__SECONDARY_NAME:
				setSecondaryName(SECONDARY_NAME_EDEFAULT);
				return;
			case CompartmentalmodelPackage.COMPARTMENT__PRODUCT:
				setProduct((Product)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CompartmentalmodelPackage.COMPARTMENT__PRIMARY_NAME:
				return PRIMARY_NAME_EDEFAULT == null ? primaryName != null : !PRIMARY_NAME_EDEFAULT.equals(primaryName);
			case CompartmentalmodelPackage.COMPARTMENT__POPULATION:
				return population != POPULATION_EDEFAULT;
			case CompartmentalmodelPackage.COMPARTMENT__OUTGOING_FLOWS:
				return outgoingFlows != null && !outgoingFlows.isEmpty();
			case CompartmentalmodelPackage.COMPARTMENT__SECONDARY_NAME:
				return SECONDARY_NAME_EDEFAULT == null ? secondaryName != null : !SECONDARY_NAME_EDEFAULT.equals(secondaryName);
			case CompartmentalmodelPackage.COMPARTMENT__PRODUCT:
				return product != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (PrimaryName: ");
		result.append(primaryName);
		result.append(", population: ");
		result.append(population);
		result.append(", SecondaryName: ");
		result.append(secondaryName);
		result.append(')');
		return result.toString();
	}

} //CompartmentImpl
