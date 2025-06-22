def next_palindrome(digit_list):
    n = len(digit_list)
    high_mid = n // 2
    low_mid = (n - 1) // 2

    while high_mid < n and low_mid >= 0:
        if digit_list[high_mid] == 9:
            digit_list[high_mid] = 0
            digit_list[low_mid] = 0
            high_mid += 1
            low_mid -= 1
        else:
            digit_list[high_mid] += 1
            if low_mid != high_mid:
                digit_list[low_mid] += 1
            return digit_list
    # If all digits processed and the palindrome needs to be larger with carry over:
    return [1] + [0] * (n - 1) + [1]
