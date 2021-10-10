#!/usr/bin/env python
"""reducer_var.py"""


import sys

SIZE = 0
MEAN = 0.0
VAR = 0.0


def m_i(cj, mj, ck, mk):
    return (cj * mj + ck * mk) / (cj + ck)


def v_i(cj, mj, vj, ck, mk, vk):
    return m_i(cj, vj, ck, vk) + cj * ck * ((mj - mk) / (cj + ck)) ** 2


for batch in sys.stdin:
    ck, mk, vk = batch.strip().split(',')
    ck = int(ck)
    mk = float(mk)
    vk = float(vk)
    VAR = v_i(SIZE, MEAN, VAR, ck, mk, vk)
    MEAN = m_i(SIZE, MEAN, ck, mk)
    SIZE += ck

sys.stdout.write("%s\n" % VAR)
