def next_palindrome(digit_list):
    n = len(digit_list)
    mid = n // 2
    left_smaller = False

    # End of left side
    i = mid - 1
    # Beginning of right side depends on odd/even length
    j = mid + 1 if (n % 2) else mid

    # Initially, ignore the middle same digits
    while i >= 0 and digit_list[i] == digit_list[j]:
        i -= 1
        j += 1

    # Check if the left side is smaller
    if i < 0 or digit_list[i] < digit_list[j]:
        left_smaller = True

    # Mirror left to right
    while i >= 0:
        digit_list[j] = digit_list[i]
        i -= 1
        j += 1

    # If left side is smaller, need to handle the increment of the middle
    if left_smaller:
        carry = 1
        i = mid - 1

        # If odd length, increment the middle digit
        if n % 2 == 1:
            digit_list[mid] += carry
            carry = digit_list[mid] // 10
            digit_list[mid] %= 10
            j = mid + 1
        else:
            j = mid

        # Propagate carry towards left and mirror to right
        while i >= 0 and carry > 0:
            digit_list[i] += carry
            carry = digit_list[i] // 10
            digit_list[i] %= 10
            digit_list[j] = digit_list[i]
            i -= 1
            j += 1

        # If carry is still left, it means we need to add 1 at start and end
        if carry > 0:
            digit_list = [1] + [0] * (n - 1) + [1]

    return digit_list
