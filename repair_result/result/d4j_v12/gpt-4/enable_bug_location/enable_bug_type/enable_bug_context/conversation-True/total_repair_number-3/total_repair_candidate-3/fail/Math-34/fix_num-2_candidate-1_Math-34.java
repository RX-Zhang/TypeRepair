public Iterator<Chromosome> iterator() {
    return new Iterator<Chromosome>() {
        private final Iterator<Chromosome> internalIterator = getChromosomeList().iterator();

        public boolean hasNext() {
            return internalIterator.hasNext();
        }

        public Chromosome next() {
            return internalIterator.next();
        }

        public void remove() {
            internalIterator.remove();
        }
    };
}
