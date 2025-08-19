/**
 */
package seirmodel.impl;

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
import seirmodel.BirthSource;
import seirmodel.Compartment;
import seirmodel.DeathSink;
import seirmodel.Group;
import seirmodel.Product;
import seirmodel.SEIRModel;
import seirmodel.SeirmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SEIR Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link seirmodel.impl.SEIRModelImpl#getCompartments <em>Compartments</em>}</li>
 *   <li>{@link seirmodel.impl.SEIRModelImpl#getBirthSources <em>Birth Sources</em>}</li>
 *   <li>{@link seirmodel.impl.SEIRModelImpl#getDeathSinks <em>Death Sinks</em>}</li>
 *   <li>{@link seirmodel.impl.SEIRModelImpl#getGroups <em>Groups</em>}</li>
 *   <li>{@link seirmodel.impl.SEIRModelImpl#getProducts <em>Products</em>}</li>
 *   <li>{@link seirmodel.impl.SEIRModelImpl#getTotalPopulation <em>Total Population</em>}</li>
 *   <li>{@link seirmodel.impl.SEIRModelImpl#getGlobalBirthRate <em>Global Birth Rate</em>}</li>
 *   <li>{@link seirmodel.impl.SEIRModelImpl#getGlobalDeathRate <em>Global Death Rate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SEIRModelImpl extends MinimalEObjectImpl.Container implements SEIRModel {
	/**
	 * The cached value of the '{@link #getCompartments() <em>Compartments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartments()
	 * @generated
	 * @ordered
	 */
	protected EList<Compartment> compartments;

	/**
	 * The cached value of the '{@link #getBirthSources() <em>Birth Sources</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBirthSources()
	 * @generated
	 * @ordered
	 */
	protected EList<BirthSource> birthSources;
	/**
	 * The cached value of the '{@link #getDeathSinks() <em>Death Sinks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeathSinks()
	 * @generated
	 * @ordered
	 */
	protected EList<DeathSink> deathSinks;
	/**
	 * The cached value of the '{@link #getGroups() <em>Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<Group> groups;

	/**
	 * The cached value of the '{@link #getProducts() <em>Products</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProducts()
	 * @generated
	 * @ordered
	 */
	protected EList<Product> products;

	/**
	 * The default value of the '{@link #getTotalPopulation() <em>Total Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalPopulation()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_POPULATION_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getTotalPopulation() <em>Total Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalPopulation()
	 * @generated
	 * @ordered
	 */
	protected int totalPopulation = TOTAL_POPULATION_EDEFAULT;
	/**
	 * The default value of the '{@link #getGlobalBirthRate() <em>Global Birth Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGlobalBirthRate()
	 * @generated
	 * @ordered
	 */
	protected static final double GLOBAL_BIRTH_RATE_EDEFAULT = 0.0;
	/**
	 * The cached value of the '{@link #getGlobalBirthRate() <em>Global Birth Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGlobalBirthRate()
	 * @generated
	 * @ordered
	 */
	protected double globalBirthRate = GLOBAL_BIRTH_RATE_EDEFAULT;
	/**
	 * The default value of the '{@link #getGlobalDeathRate() <em>Global Death Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGlobalDeathRate()
	 * @generated
	 * @ordered
	 */
	protected static final double GLOBAL_DEATH_RATE_EDEFAULT = 0.0;
	/**
	 * The cached value of the '{@link #getGlobalDeathRate() <em>Global Death Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGlobalDeathRate()
	 * @generated
	 * @ordered
	 */
	protected double globalDeathRate = GLOBAL_DEATH_RATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SEIRModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SeirmodelPackage.Literals.SEIR_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Compartment> getCompartments() {
		if (compartments == null) {
			compartments = new EObjectContainmentEList<Compartment>(Compartment.class, this, SeirmodelPackage.SEIR_MODEL__COMPARTMENTS);
		}
		return compartments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<BirthSource> getBirthSources() {
		if (birthSources == null) {
			birthSources = new EObjectContainmentEList<BirthSource>(BirthSource.class, this, SeirmodelPackage.SEIR_MODEL__BIRTH_SOURCES);
		}
		return birthSources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DeathSink> getDeathSinks() {
		if (deathSinks == null) {
			deathSinks = new EObjectContainmentEList<DeathSink>(DeathSink.class, this, SeirmodelPackage.SEIR_MODEL__DEATH_SINKS);
		}
		return deathSinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Group> getGroups() {
		if (groups == null) {
			groups = new EObjectContainmentEList<Group>(Group.class, this, SeirmodelPackage.SEIR_MODEL__GROUPS);
		}
		return groups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Product> getProducts() {
		if (products == null) {
			products = new EObjectContainmentEList<Product>(Product.class, this, SeirmodelPackage.SEIR_MODEL__PRODUCTS);
		}
		return products;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getTotalPopulation() {
		return totalPopulation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTotalPopulation(int newTotalPopulation) {
		int oldTotalPopulation = totalPopulation;
		totalPopulation = newTotalPopulation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SeirmodelPackage.SEIR_MODEL__TOTAL_POPULATION, oldTotalPopulation, totalPopulation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getGlobalBirthRate() {
		return globalBirthRate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGlobalBirthRate(double newGlobalBirthRate) {
		double oldGlobalBirthRate = globalBirthRate;
		globalBirthRate = newGlobalBirthRate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SeirmodelPackage.SEIR_MODEL__GLOBAL_BIRTH_RATE, oldGlobalBirthRate, globalBirthRate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getGlobalDeathRate() {
		return globalDeathRate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGlobalDeathRate(double newGlobalDeathRate) {
		double oldGlobalDeathRate = globalDeathRate;
		globalDeathRate = newGlobalDeathRate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SeirmodelPackage.SEIR_MODEL__GLOBAL_DEATH_RATE, oldGlobalDeathRate, globalDeathRate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SeirmodelPackage.SEIR_MODEL__COMPARTMENTS:
				return ((InternalEList<?>)getCompartments()).basicRemove(otherEnd, msgs);
			case SeirmodelPackage.SEIR_MODEL__BIRTH_SOURCES:
				return ((InternalEList<?>)getBirthSources()).basicRemove(otherEnd, msgs);
			case SeirmodelPackage.SEIR_MODEL__DEATH_SINKS:
				return ((InternalEList<?>)getDeathSinks()).basicRemove(otherEnd, msgs);
			case SeirmodelPackage.SEIR_MODEL__GROUPS:
				return ((InternalEList<?>)getGroups()).basicRemove(otherEnd, msgs);
			case SeirmodelPackage.SEIR_MODEL__PRODUCTS:
				return ((InternalEList<?>)getProducts()).basicRemove(otherEnd, msgs);
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
			case SeirmodelPackage.SEIR_MODEL__COMPARTMENTS:
				return getCompartments();
			case SeirmodelPackage.SEIR_MODEL__BIRTH_SOURCES:
				return getBirthSources();
			case SeirmodelPackage.SEIR_MODEL__DEATH_SINKS:
				return getDeathSinks();
			case SeirmodelPackage.SEIR_MODEL__GROUPS:
				return getGroups();
			case SeirmodelPackage.SEIR_MODEL__PRODUCTS:
				return getProducts();
			case SeirmodelPackage.SEIR_MODEL__TOTAL_POPULATION:
				return getTotalPopulation();
			case SeirmodelPackage.SEIR_MODEL__GLOBAL_BIRTH_RATE:
				return getGlobalBirthRate();
			case SeirmodelPackage.SEIR_MODEL__GLOBAL_DEATH_RATE:
				return getGlobalDeathRate();
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
			case SeirmodelPackage.SEIR_MODEL__COMPARTMENTS:
				getCompartments().clear();
				getCompartments().addAll((Collection<? extends Compartment>)newValue);
				return;
			case SeirmodelPackage.SEIR_MODEL__BIRTH_SOURCES:
				getBirthSources().clear();
				getBirthSources().addAll((Collection<? extends BirthSource>)newValue);
				return;
			case SeirmodelPackage.SEIR_MODEL__DEATH_SINKS:
				getDeathSinks().clear();
				getDeathSinks().addAll((Collection<? extends DeathSink>)newValue);
				return;
			case SeirmodelPackage.SEIR_MODEL__GROUPS:
				getGroups().clear();
				getGroups().addAll((Collection<? extends Group>)newValue);
				return;
			case SeirmodelPackage.SEIR_MODEL__PRODUCTS:
				getProducts().clear();
				getProducts().addAll((Collection<? extends Product>)newValue);
				return;
			case SeirmodelPackage.SEIR_MODEL__TOTAL_POPULATION:
				setTotalPopulation((Integer)newValue);
				return;
			case SeirmodelPackage.SEIR_MODEL__GLOBAL_BIRTH_RATE:
				setGlobalBirthRate((Double)newValue);
				return;
			case SeirmodelPackage.SEIR_MODEL__GLOBAL_DEATH_RATE:
				setGlobalDeathRate((Double)newValue);
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
			case SeirmodelPackage.SEIR_MODEL__COMPARTMENTS:
				getCompartments().clear();
				return;
			case SeirmodelPackage.SEIR_MODEL__BIRTH_SOURCES:
				getBirthSources().clear();
				return;
			case SeirmodelPackage.SEIR_MODEL__DEATH_SINKS:
				getDeathSinks().clear();
				return;
			case SeirmodelPackage.SEIR_MODEL__GROUPS:
				getGroups().clear();
				return;
			case SeirmodelPackage.SEIR_MODEL__PRODUCTS:
				getProducts().clear();
				return;
			case SeirmodelPackage.SEIR_MODEL__TOTAL_POPULATION:
				setTotalPopulation(TOTAL_POPULATION_EDEFAULT);
				return;
			case SeirmodelPackage.SEIR_MODEL__GLOBAL_BIRTH_RATE:
				setGlobalBirthRate(GLOBAL_BIRTH_RATE_EDEFAULT);
				return;
			case SeirmodelPackage.SEIR_MODEL__GLOBAL_DEATH_RATE:
				setGlobalDeathRate(GLOBAL_DEATH_RATE_EDEFAULT);
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
			case SeirmodelPackage.SEIR_MODEL__COMPARTMENTS:
				return compartments != null && !compartments.isEmpty();
			case SeirmodelPackage.SEIR_MODEL__BIRTH_SOURCES:
				return birthSources != null && !birthSources.isEmpty();
			case SeirmodelPackage.SEIR_MODEL__DEATH_SINKS:
				return deathSinks != null && !deathSinks.isEmpty();
			case SeirmodelPackage.SEIR_MODEL__GROUPS:
				return groups != null && !groups.isEmpty();
			case SeirmodelPackage.SEIR_MODEL__PRODUCTS:
				return products != null && !products.isEmpty();
			case SeirmodelPackage.SEIR_MODEL__TOTAL_POPULATION:
				return totalPopulation != TOTAL_POPULATION_EDEFAULT;
			case SeirmodelPackage.SEIR_MODEL__GLOBAL_BIRTH_RATE:
				return globalBirthRate != GLOBAL_BIRTH_RATE_EDEFAULT;
			case SeirmodelPackage.SEIR_MODEL__GLOBAL_DEATH_RATE:
				return globalDeathRate != GLOBAL_DEATH_RATE_EDEFAULT;
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
		result.append(" (totalPopulation: ");
		result.append(totalPopulation);
		result.append(", globalBirthRate: ");
		result.append(globalBirthRate);
		result.append(", globalDeathRate: ");
		result.append(globalDeathRate);
		result.append(')');
		return result.toString();
	}

} //SEIRModelImpl
