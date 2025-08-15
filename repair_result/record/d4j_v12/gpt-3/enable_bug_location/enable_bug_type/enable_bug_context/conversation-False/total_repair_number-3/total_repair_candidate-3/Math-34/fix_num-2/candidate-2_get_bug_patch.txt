public Iterator<Chromosome> iterator() {
    // Wrapping the iterator to throw an exception when remove is called
    return new Iterator<Chromosome>() {
        private final Iterator<Chromosome> iter = chromosomes.iterator();

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public Chromosome next() {
            return iter.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.");
        }
    };
}
