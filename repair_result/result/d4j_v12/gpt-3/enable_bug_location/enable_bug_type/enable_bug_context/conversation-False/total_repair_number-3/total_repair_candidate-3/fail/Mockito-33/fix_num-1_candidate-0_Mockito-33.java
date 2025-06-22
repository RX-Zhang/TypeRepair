public boolean hasSameMethod(Invocation candidate) {        
    //not using method.equals() for 1 good reason:
    //sometimes java generates forwarding methods when generics are in play see JavaGenericsForwardingMethodsTest
    Method m1 = invocation.getMethod();
    Method m2 = candidate.getMethod();
    
    // Check for null to prevent potential NullPointerException
    if (m1 == null || m2 == null) {
        return false;
    }
    
    /* Avoid unnecessary cloning */
    return m1.equals(m2);
}
