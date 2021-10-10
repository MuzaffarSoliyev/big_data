#!/usr/bin/env python
"""reducer_mean.py"""


import sys

SIZE = 0
MEAN = 0.0


def m_i(cj, mj, ck, mk):
    return (cj * mj + ck * mk) / (cj + ck)


for batch in sys.stdin:
    ck, mk = batch.strip().split(',')
    ck = int(ck)
    mk = float(mk)
    MEAN = m_i(SIZE, MEAN, ck, mk)
    SIZE += ck

sys.stdout.write("%s\n" % MEAN)
