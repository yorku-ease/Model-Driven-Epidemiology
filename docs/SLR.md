# This document presents the evolution of the SLR ([Systematic Litterature Review](https://en.wikipedia.org/wiki/Systematic_review))

## Exploration phase

During this first phase, the direction of the project is still not certain, we are looking for tools, articles, methods, etc.

### Queries and results

#### 2021-11-10

Query: `"covid" "modeling" "software" "tools"`

Interesting results:

[Epidemiological modeling in StochSS Live!](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC7929247/)

That tools lets us quite easily define models, it seems to lack some functionalities but given the fact that it is available in a browser, it is understandable and very valuable. It is interesting that the tool is meant for modeling potentially epidemics but also chemical reactions.

example:

- S: succeptible
- I: infected
- D: dead
- R: fake recovery state in which you can become succeptible again
- R2: immune
- do not mind the numbers, this is just an example
![image](https://user-images.githubusercontent.com/43907476/141230852-a3f20bdb-3a44-4f9c-8cdd-f6b67eff8fad.png)

Moving forward:
The query is too broad, we must exclude chemical modeling for covid proteins, it is also too specific since articles about epidemiological modeling that do not mention covid (either before 2020, or for other reasons) will be wrongfully filtered out by the covid keyword, that being said, the covid keyword is very powerful since it provides us with recent articles and lets us define the question more broadly, since anything related to covid might be interesting to know about now, even if the query is refined later.
