/**
 */
package compartmentGroup.provider;

import compartmentGroup.CompartmentGroupFactory;
import compartmentGroup.CompartmentGroupPackage;
import compartmentGroup.GroupEpidemic;

import epimodel.EpimodelFactory;

import epimodel.provider.EpidemicItemProvider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link compartmentGroup.GroupEpidemic} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class GroupEpidemicItemProvider extends EpidemicItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupEpidemicItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(CompartmentGroupPackage.Literals.GROUP_EPIDEMIC__GROUP_SINKS);
			childrenFeatures.add(CompartmentGroupPackage.Literals.GROUP_EPIDEMIC__GROUP_SOURCES);
			childrenFeatures.add(CompartmentGroupPackage.Literals.GROUP_EPIDEMIC__FLOW);
			childrenFeatures.add(CompartmentGroupPackage.Literals.GROUP_EPIDEMIC__COMPARTMENT);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns GroupEpidemic.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/GroupEpidemic"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage() {
		return true;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((GroupEpidemic) object).getId();
		return label == null || label.length() == 0 ? getString("_UI_GroupEpidemic_type")
				: getString("_UI_GroupEpidemic_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(GroupEpidemic.class)) {
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SINKS:
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SOURCES:
		case CompartmentGroupPackage.GROUP_EPIDEMIC__FLOW:
		case CompartmentGroupPackage.GROUP_EPIDEMIC__COMPARTMENT:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(CompartmentGroupPackage.Literals.GROUP_EPIDEMIC__GROUP_SINKS,
				CompartmentGroupFactory.eINSTANCE.createGroupSinks()));

		newChildDescriptors.add(createChildParameter(CompartmentGroupPackage.Literals.GROUP_EPIDEMIC__GROUP_SOURCES,
				CompartmentGroupFactory.eINSTANCE.createGroupSources()));

		newChildDescriptors.add(createChildParameter(CompartmentGroupPackage.Literals.GROUP_EPIDEMIC__FLOW,
				EpimodelFactory.eINSTANCE.createFlowWrapper()));

		newChildDescriptors.add(createChildParameter(CompartmentGroupPackage.Literals.GROUP_EPIDEMIC__COMPARTMENT,
				EpimodelFactory.eINSTANCE.createCompartmentWrapper()));
	}

}
