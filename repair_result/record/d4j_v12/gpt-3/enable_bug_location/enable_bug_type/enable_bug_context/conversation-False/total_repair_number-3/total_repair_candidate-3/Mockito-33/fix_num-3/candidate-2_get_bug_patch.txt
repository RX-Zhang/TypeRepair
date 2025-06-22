public boolean hasSameMethod(Invocation candidate) {        
    //not using method.equals() for 1 good reason:
    //sometimes java generates forwarding methods when generics are in play see JavaGenericsForwardingMethodsTest
    Method m1 = invocation.getMethod();
    Method m2 = candidate.getMethod();
    
    /* Avoid unnecessary cloning */
    // Correcting the logical error; we should also check if m1 and m2 are not null before comparing
    return m1 != null && m2 != null && m1.equals(m2);
}
