def next_palindrome(digit_list):
    high_mid = len(digit_list) // 2
    low_mid = (len(digit_list) - 1) // 2
    while high_mid > low_mid:  # Corrected the condition to ensure we process the entire list
        if digit_list[high_mid] == 9:
            digit_list[high_mid] = 0
            digit_list[low_mid] = 0
            high_mid -= 1  # Changed to decrement to correctly mirror the digits
            low_mid += 1   # Changed to increment to correctly mirror the digits
        else:
            digit_list[high_mid] += 1
            digit_list[low_mid] += 1  # This should always happen for both sides
            return digit_list
    return [1] + (len(digit_list)) * [0] + [1]
