def next_palindrome(digit_list):
    n = len(digit_list)
    low_mid = (n - 1) // 2
    high_mid = n // 2

    while low_mid >= 0:
        if digit_list[low_mid] == 9:
            digit_list[low_mid] = 0
            digit_list[high_mid] = 0
            low_mid -= 1
            high_mid += 1
        else:
            digit_list[low_mid] += 1
            if high_mid != low_mid:
                digit_list[high_mid] += 1
            return digit_list
    return [1] + [0] * n + [1]
