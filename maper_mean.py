#!/usr/bin/env python
"""maper_mean.py"""


import sys
import numpy as np

for batch in sys.stdin:
    prices_batch_list = np.array(list(map(float, batch.strip().split())))
    ck = len(prices_batch_list)
    mk = np.mean(prices_batch_list)
    sys.stdout.write("%s,%s\n" % (ck, mk))
