public Iterator<Chromosome> iterator() {
    return new Iterator<Chromosome>() {
        private final Iterator<Chromosome> iterator = chromosomes.iterator();
        private Chromosome lastReturned = null;

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Chromosome next() {
            lastReturned = iterator.next();
            return lastReturned;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException("next() has not been called before remove()");
            }
            iterator.remove();
            lastReturned = null;
        }
    };
}
