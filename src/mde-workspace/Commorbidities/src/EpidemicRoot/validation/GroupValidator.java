/**
 *
 * $Id$
 */
package EpidemicRoot.validation;

import EpidemicRoot.AbstractCompartment;
import EpidemicRoot.Flow;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link EpidemicRoot.Group}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface GroupValidator {
	boolean validate();

	boolean validateFlows(EList<Flow> value);
	boolean validateCompartments(EList<AbstractCompartment> value);
	boolean validateSink(EList<AbstractCompartment> value);
	boolean validateSource(EList<AbstractCompartment> value);
}
