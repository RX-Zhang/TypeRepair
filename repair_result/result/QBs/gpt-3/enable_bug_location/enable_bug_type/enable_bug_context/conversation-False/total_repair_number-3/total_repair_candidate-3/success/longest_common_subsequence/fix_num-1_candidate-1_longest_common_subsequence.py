def longest_common_subsequence(a, b):
    if not a or not b:
        return ''
    elif a[0] == b[0]:
        return a[0] + longest_common_subsequence(a[1:], b[1:])
    else:
        lcs1 = longest_common_subsequence(a, b[1:])
        lcs2 = longest_common_subsequence(a[1:], b)
        return lcs1 if len(lcs1) > len(lcs2) else lcs2
