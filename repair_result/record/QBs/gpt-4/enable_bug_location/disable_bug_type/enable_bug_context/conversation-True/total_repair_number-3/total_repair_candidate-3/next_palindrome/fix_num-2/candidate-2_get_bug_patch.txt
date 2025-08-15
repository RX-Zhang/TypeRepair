def next_palindrome(digit_list):
    n = len(digit_list)
    low_mid = (n - 1) // 2
    high_mid = n // 2

    # Move outwards to handle the palindrome creation
    while low_mid >= 0 and high_mid < n:
        if digit_list[low_mid] == 9 and digit_list[high_mid] == 9:
            digit_list[low_mid] = 0
            digit_list[high_mid] = 0
            low_mid -= 1
            high_mid += 1
        else:
            digit_list[low_mid] += 1
            digit_list[high_mid] = digit_list[low_mid]
            return digit_list

    # If all digits were 9’s or palindrome has been reset to 0,
    # new palindrome is 1 followed by zeros and ending with 1
    return [1] + [0] * (n - 1) + [1]
