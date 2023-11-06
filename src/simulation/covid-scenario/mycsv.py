def transform(point, transforms):
    if transforms == []:
        return point
    else:
        if len(point) != len(transforms):
            raise Exception(f'point has length {len(point)} and transforms has length {len(transforms)}')
        print(point)
        return [f(p) for f, p in zip(transforms, point)]

class CSV:
    def __init__(self, content, delim, transforms=[]):
        if type(content) == str:
            content = open(content).readlines()
        self.headers = list(map(lambda header: header.strip(), content[0].split(delim)))
        self.data = list(map(lambda line: transform(line.split(delim), transforms), content[1:]))
    
    def get(self, *cols):
        indices = []
        for search in cols:
            for i, col in enumerate(self.headers):
                if search == col:
                    indices += [i]
                    break
            else:
                raise Exception(f"Column not found '{search}', available columns are: " + str(self.headers))
        for point in self.data:
            yield [point[i] for i in indices]
    
    def get_simple(self, col):
        if col not in self.headers:
            raise Exception(f"Column not found '{col}', available columns are: " + str(self.headers))
        for point in self.get(col):
            yield point[0]
    
    def __str__(self):
        return 'headers = ' + str(self.headers) +\
            '\ndata=\n\t' + '\n\t'.join(map(str, self.data))
