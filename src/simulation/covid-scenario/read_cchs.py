from covid_scenario import CSV

comorbidities = ['cancer', 'high blood pressure', 'diabetes', 'asthma', 'heart disease', 'stroke']
n_comorbidity_columns = len(comorbidities)
ages = ['12 TO 14 YEARS', '15 TO 17 YEARS', '18 TO 19 YEARS', '20 TO 24 YEARS', '25 TO 29 YEARS', '30 TO 34 YEARS', '35 TO 39 YEARS', '40 TO 44 YEARS', '45 TO 49 YEARS', '50 TO 54 YEARS', '55 TO 59 YEARS', '60 TO 64 YEARS', '65 TO 69 YEARS', '70 TO 74 YEARS', '75 TO 79 YEARS', '80 YEARS OR OLDER']
n_age_columns = len(ages)

# cchs1.csv is created using protected data from the canadian government
# data can be requested at https://www150.statcan.gc.ca/n1/en/catalogue/82M0013X2016001
# do not distribute cchs1.csv, share privately within the team if required
# cchs1 is built aggregating the aforementioned comorbidities and age groups
# the file is built using beyond 20/20 and exporting to csv, no modification needed
csv = CSV(open('cchs1.csv').readlines(), ',')

# this script takes the file as input and generates a file that can be publicly shared
# the generated file is required for our epidemiological model of Covid-19

# users with access may verify that they created
# their file properly by comparing their output
# with the comments, do not remove the comments
# as it will be harder to reproduce the results without
# step by step validation

print(len(csv.data)) # 117649
# the + 1 column is the TOTAL for age
print(len(csv.headers), len(csv.headers) == n_age_columns + n_comorbidity_columns + 1) # 23, True
# order of the comorbidities does not matter
print(csv.headers) # ['CCC_101', 'CCC_151', 'CCC_031', 'CCC_071', 'CCC_121', 'CCC_131', 'Total', '12 TO 14 YEARS', '15 TO 17 YEARS', '18 TO 19 YEARS', '20 TO 24 YEARS', '25 TO 29 YEARS', '30 TO 34 YEARS', '35 TO 39 YEARS', '40 TO 44 YEARS', '45 TO 49 YEARS', '50 TO 54 YEARS', '55 TO 59 YEARS', '60 TO 64 YEARS', '65 TO 69 YEARS', '70 TO 74 YEARS', '75 TO 79 YEARS', '80 YEARS OR OLDER']

def add_if_comorbidity(row, start_at, into):
    if row[start_at] == 'YES':
        for element in row[start_at + 1:n_comorbidity_columns]:
            if element == 'YES' or element == 'Total':
                return
        for i, val in enumerate(row[-n_age_columns:]):
            if val != '-' and val != '-\n':
                into[i] += int(val)

def add2(row, into):
    has_yes = False
    for element in row[:n_comorbidity_columns]:
        if element == 'YES':
            has_yes = True
        elif element == 'Total':
            return
    if has_yes:
        for i, val in enumerate(row[-n_age_columns:]):
            if val != '-' and val != '-\n':
                into[i] += int(val)

csv.data  = csv.data
vals = [0] * n_age_columns
for row in csv.data:
#    for i in range(n_comorbidity_columns):
    # add_if_comorbidity(row, 0, vals)
    add2(row, vals)

print(sum(vals))
print(vals)