def next_palindrome(digit_list):
    n = len(digit_list)
    if all(d == 9 for d in digit_list):
        return [1] + [0]*(n-1) + [1]
    mid = n // 2
    leftsmaller = False

    # End of left side
    i = mid - 1
    # Beginning of right side
    j = mid + 1 if n % 2 else mid

    # Initially, skip the middle same digits
    while i >= 0 and digit_list[i] == digit_list[j]:
        i -= 1
        j += 1

    # Find if the left side is smaller
    if i < 0 or digit_list[i] < digit_list[j]:
        leftsmaller = True

    # Mirror left to right
    while i >= 0:
        digit_list[j] = digit_list[i]
        i -= 1
        j += 1

    # Handle the case where left side is smaller
    if leftsmaller:
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
            digit_list[j] = digit_list[i]  # Copy mirror
            i -= 1
            j += 1

    return digit_list
