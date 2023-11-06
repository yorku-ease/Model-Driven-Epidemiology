import sys
sys.path.append('..')
from mycsv import CSV

# using https://www.researchgate.net/publication/232701632_POLYMOD_contact_survey_for_researchers
# maybe this is the right repo? https://zenodo.org/records/1157934
# see https://github.com/epiforecasts/socialmixr/issues/1

# this script produces polymod_matrix.csv used for simulation parameterization

participant_data = CSV('2008_Mossong_POLYMOD/participant_common.csv', ',')
contact_data = CSV('2008_Mossong_POLYMOD/contact_common.csv', ',')

participant_id_to_age = {id: age for id, age in participant_data.get('part_id', 'part_age')}
