from mycsv import CSV

do_print = __name__ == "__main__"

comorbidities = ['cancer', 'high blood pressure', 'diabetes', 'asthma', 'heart disease', 'stroke']
n_comorbidity_columns = len(comorbidities)
ages = ['12 TO 14 YEARS', '15 TO 17 YEARS', '18 TO 19 YEARS', '20 TO 24 YEARS', '25 TO 29 YEARS', '30 TO 34 YEARS', '35 TO 39 YEARS', '40 TO 44 YEARS', '45 TO 49 YEARS', '50 TO 54 YEARS', '55 TO 59 YEARS', '60 TO 64 YEARS', '65 TO 69 YEARS', '70 TO 74 YEARS', '75 TO 79 YEARS', '80 YEARS OR OLDER']
n_age_columns = len(ages)

# cchs1.csv is created using protected data from the canadian government
# data can be requested at https://www150.statcan.gc.ca/n1/en/catalogue/82M0013X2016001
# do not distribute cchs1.csv, share privately within the team if required
# cchs1.csv is built aggregating the aforementioned comorbidities and age groups
# the file is built using beyond 20/20 and exporting to csv, no modification needed
# in beyond 20/20 put the age groups as columns and diseases as rows (drag all 6 on the left, they will indent)
csv = CSV('C:/Users/Bruno/Desktop/cchs1.csv', ',')


# users with access to the original data may verify that they created
# their cchs1.csv file properly by comparing their output
# with the comments, do not remove the comments
# as it will be harder to reproduce the results without
# step by step validation
if do_print:
    print(len(csv.data)) # 117649 (117649 = 7^6 = (number of answers per questions)^(number of questions))
    # the + 1 is the Total column for age
    print(len(csv.headers) == n_age_columns + 1 + n_comorbidity_columns) # True

if do_print:
    # note that the order of the comorbidities does not matter
    print(csv.headers) # ['CCC_101', 'CCC_151', 'CCC_031', 'CCC_071', 'CCC_121', 'CCC_131', 'Total', '12 TO 14 YEARS', '15 TO 17 YEARS', '18 TO 19 YEARS', '20 TO 24 YEARS', '25 TO 29 YEARS', '30 TO 34 YEARS', '35 TO 39 YEARS', '40 TO 44 YEARS', '45 TO 49 YEARS', '50 TO 54 YEARS', '55 TO 59 YEARS', '60 TO 64 YEARS', '65 TO 69 YEARS', '70 TO 74 YEARS', '75 TO 79 YEARS', '80 YEARS OR OLDER']

# this script takes the file as input and generates a file that can be publicly shared
# the generated file is required for our epidemiological model of Covid-19

pop_by_age = list(map(int, csv.data[0][-n_age_columns:]))
if do_print:
    print(pop_by_age) # [1088948, 1272998, 817136, 2418278, 2372150, 2320556, 2295442, 2414213, 2333830, 2707880, 2575488, 2183497, 1821531, 1301035, 946056, 1133779]

def add_if_comorbidity(row, into):
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

comorbidity_by_age = [0] * n_age_columns
for row in csv.data:
    add_if_comorbidity(row, comorbidity_by_age)
p_comorbidity_by_age = list(map(lambda pair: pair[0] / pair[1], zip(comorbidity_by_age, pop_by_age)))

if do_print:
    print(comorbidity_by_age) # [119742, 133823, 102439, 299146, 300950, 262045, 336907, 458766, 571581, 844088, 997026, 1049674, 1025910, 835051, 648777, 800672]
    print(p_comorbidity_by_age) # [0.10996117353629374, 0.10512428142070922, 0.12536346458851402, 0.12370207230103404, 0.12686803111101744, 0.11292336836516766, 0.14677216849739613, 0.19002714342106516, 0.24491115462565824, 0.31171543790714507, 0.3871211980020874, 0.4807306811046683, 0.563213033431767, 0.6418359229382761, 0.6857701869656765, 0.7061975922997339]

with open('cchs-out.csv', 'w+') as f:
    f.write('age,p-comorbidity\n')
    for age, p in zip(ages, p_comorbidity_by_age):
        f.write(age.lower() + f',{p}\n')