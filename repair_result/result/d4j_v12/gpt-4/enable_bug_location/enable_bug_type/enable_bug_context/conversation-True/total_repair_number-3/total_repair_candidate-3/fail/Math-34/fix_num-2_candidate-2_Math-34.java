public Iterator<Chromosome> iterator() {
    return new Iterator<Chromosome>() {
        private final Iterator<Chromosome> iter = getChromosomeList().iterator();

        public boolean hasNext() {
            return iter.hasNext();
        }

        public Chromosome next() {
            return iter.next();
        }

        public void remove() {
            iter.remove();
        }
    };
}
