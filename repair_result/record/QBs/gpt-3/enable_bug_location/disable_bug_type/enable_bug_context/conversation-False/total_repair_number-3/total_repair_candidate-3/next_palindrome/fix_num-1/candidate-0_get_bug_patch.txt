def next_palindrome(digit_list):
    n = len(digit_list)
    high_mid = n // 2
    low_mid = (n - 1) // 2

    # Move from middle to the ends, handling 9s and incrementing
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
    # If all digits in the middle have been set to 0, handle overflow
    return [1] + [0] * n + [1]
