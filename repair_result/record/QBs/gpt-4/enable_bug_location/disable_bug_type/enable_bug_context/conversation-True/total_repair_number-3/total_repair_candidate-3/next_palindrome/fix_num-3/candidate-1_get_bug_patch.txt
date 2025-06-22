def next_palindrome(digit_list):
    n = len(digit_list)
    mid = n // 2
    left_smaller = False
    i = mid - 1
    j = mid + 1 if n % 2 else mid

    while i >= 0 and digit_list[i] == digit_list[j]:
        i -= 1
        j += 1

    if i < 0 or digit_list[i] < digit_list[j]:
        left_smaller = True

    while i >= 0:
        digit_list[j] = digit_list[i]
        i -= 1
        j += 1

    if left_smaller:
        carry = 1
        i = mid - 1

        if n % 2 == 1:
            digit_list[mid] += carry
            carry = digit_list[mid] // 10
            digit_list[mid] %= 10
            j = mid + 1
        else:
            j = mid

        while i >= 0 and carry > 0:
            digit_list[i] += carry
            carry = digit_list[i] // 10
            digit_list[i] %= 10
            digit_list[j] = digit_list[i]
            i -= 1
            j += 1

        if carry > 0:
            digit_list = [1] + [0] * (n - 1) + [1]

    return digit_list
