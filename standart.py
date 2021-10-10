import numpy as np

with open('prices.txt', 'r') as f:
    data = list(map(float, f.read().strip().split(',')))
    print(np.mean(data))
    print(np.var(data))