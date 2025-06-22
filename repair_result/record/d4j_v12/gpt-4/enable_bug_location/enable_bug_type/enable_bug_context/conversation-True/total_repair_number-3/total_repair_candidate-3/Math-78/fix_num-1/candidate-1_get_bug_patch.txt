public boolean evaluateStep(final StepInterpolator interpolator)
    throws DerivativeException, EventException, ConvergenceException {

    try {

        forward = interpolator.isForward();
        final double t1 = interpolator.getCurrentTime();
        final int    n  = Math.max(1, (int) Math.ceil(Math.abs(t1 - t0) / maxCheckInterval));
        final double h  = (t1 - t0) / n;

        double ta = t0;
        double ga = g0;
        double tb = t0 + (interpolator.isForward() ? convergence : -convergence);

        // Ensure that the initial bracket [ta, tb] has function values of opposite signs
        interpolator.setInterpolatedTime(ta);
        ga = handler.g(ta, interpolator.getInterpolatedState());
        interpolator.setInterpolatedTime(tb);
        double gb = handler.g(tb, interpolator.getInterpolatedState());

        if ((ga == 0.0) || (gb == 0.0) || (ga * gb > 0.0)) {
            // Expand the bracket if no sign change between ga and gb
            // Shift tb away from ta until we find a sign change or reach t1
            double step = (interpolator.isForward() ? convergence : -convergence);
            double newTb = tb;
            boolean signChangeFound = false;
            while (!signChangeFound) {
                newTb += step;
                if ((interpolator.isForward() && newTb > t1) || (!interpolator.isForward() && newTb < t1)) {
                    break;
                }
                interpolator.setInterpolatedTime(newTb);
                gb = handler.g(newTb, interpolator.getInterpolatedState());
                if (ga * gb <= 0.0) {
                    signChangeFound = true;
                    tb = newTb;
                }
            }
            if (!signChangeFound) {
                // No sign change found, skip event detection in this step
                ta = tb;
                ga = gb;
            }
        }

        for (int i = 0; i < n; ++i) {

            // evaluate handler value at the end of the substep
            tb += h;
            if ((interpolator.isForward() && tb > t1) || (!interpolator.isForward() && tb < t1)) {
                tb = t1;
            }
            interpolator.setInterpolatedTime(tb);
            gb = handler.g(tb, interpolator.getInterpolatedState());

            // check events occurrence
            if (g0Positive ^ (gb >= 0)) {
                // there is a sign change: an event is expected during this step

                // variation direction, with respect to the integration direction
                increasing = gb >= ga;

                final UnivariateRealFunction f = new UnivariateRealFunction() {
                    public double value(final double t) throws FunctionEvaluationException {
                        try {
                            interpolator.setInterpolatedTime(t);
                            return handler.g(t, interpolator.getInterpolatedState());
                        } catch (DerivativeException e) {
                            throw new FunctionEvaluationException(e, t);
                        } catch (EventException e) {
                            throw new FunctionEvaluationException(e, t);
                        }
                    }
                };
                final BrentSolver solver = new BrentSolver();
                solver.setAbsoluteAccuracy(convergence);
                solver.setMaximalIterationCount(maxIterationCount);
                final double root = (ta <= tb) ? solver.solve(f, ta, tb) : solver.solve(f, tb, ta);
                if ((Math.abs(root - ta) <= convergence) &&
                     (Math.abs(root - previousEventTime) <= convergence)) {
                    // we have either found nothing or found (again ?) a past event, we simply ignore it
                    ta = tb;
                    ga = gb;
                } else if (Double.isNaN(previousEventTime) ||
                           (Math.abs(previousEventTime - root) > convergence)) {
                    pendingEventTime = root;
                    if (pendingEvent && (Math.abs(t1 - pendingEventTime) <= convergence)) {
                        // we were already waiting for this event which was
                        // found during a previous call for a step that was
                        // rejected, this step must now be accepted since it
                        // properly ends exactly at the event occurrence
                        return false;
                    }
                    // either we were not waiting for the event or it has
                    // moved in such a way the step cannot be accepted
                    pendingEvent = true;
                    return true;
                }

            } else {
                // no sign change: there is no event for now
                ta = tb;
                ga = gb;
            }

        }

        // no event during the whole step
        pendingEvent     = false;
        pendingEventTime = Double.NaN;
        return false;

    } catch (FunctionEvaluationException e) {
        final Throwable cause = e.getCause();
        if ((cause != null) && (cause instanceof DerivativeException)) {
            throw (DerivativeException) cause;
        } else if ((cause != null) && (cause instanceof EventException)) {
            throw (EventException) cause;
        }
        throw new EventException(e);
    }

}
