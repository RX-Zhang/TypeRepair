public Iterator<Chromosome> iterator() {
    return new Iterator<Chromosome>() {
        private final Iterator<Chromosome> innerIter = getChromosomeList().iterator();

        public boolean hasNext() {
            return innerIter.hasNext();
        }

        public Chromosome next() {
            return innerIter.next();
        }

        public void remove() {
            innerIter.remove();
        }
    };
}
