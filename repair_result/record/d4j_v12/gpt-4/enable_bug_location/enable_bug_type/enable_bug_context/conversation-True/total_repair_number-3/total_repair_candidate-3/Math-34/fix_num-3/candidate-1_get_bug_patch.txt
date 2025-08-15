public Iterator<Chromosome> iterator() {
    final List<Chromosome> copy = new ArrayList<>(chromosomes);
    return new Iterator<Chromosome>() {
        private final Iterator<Chromosome> iter = copy.iterator();

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
            throw new UnsupportedOperationException("Remove not supported on this iterator");
        }
    };
}
