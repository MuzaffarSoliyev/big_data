#!/usr/bin/env python
"""maper_var.py"""


import sys
import numpy as np


for batch in sys.stdin:
    prices_batch_list = list(map(float, batch.strip().split()))
    ck = len(prices_batch_list)
    mk = np.mean(prices_batch_list)
    vk = np.var(prices_batch_list)
    sys.stdout.write("%s,%s,%s\n" % (ck, mk, vk))
