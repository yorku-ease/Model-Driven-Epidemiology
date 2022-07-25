
**Problème points d'extensions**
https://www.eclipse.org/forums/index.php/m/1083077/?srch=customize+diff#msg_1083077

**Guide Dev EMFCompare**
https://www.eclipse.org/emf/compare/documentation/latest/developer/developer-guide.html

**Sources (repo git) EMFCompare**
https://git.eclipse.org/c/emfcompare/org.eclipse.emf.compare.git/tree/plugins/org.eclipse.emf.compare

**MatchOfValue**
https://stackoverflow.com/questions/62171658/retrieving-the-match-of-value-in-an-add-diff-computed-by-emf-compare

**Infos sur la comparaison**

Le Match et le diff se déroule lors de l'executions de diffEngine.diff(comparison, subMonitor) dans le fichier EMFCompare.java;
Dans DefaultDiffEngine.java se passe le calcul des différences et l'attribution des Kinds mais aussi les filtres d'attributs et de references 

Atention particulières sur les fonctions :


	protected void computeSingleValuedReferenceDifferencesTwoWay(Match match, EReference reference);
  protected FeatureFilter createFeatureFilter();
	protected void featureChange(Match match, EStructuralFeature feature, Object value, DifferenceKind kind,DifferenceSource source) 

Pour le calcul de distance voir le fichier EditionDistance.java : 

attributeChange(Match match, EAttribute attribute, Object value, DifferenceKind kind,DifferenceSource source)
