public Iterator<Chromosome> iterator() {
    return new Iterator<Chromosome>() {
        private final Iterator<Chromosome> iter = chromosomes.iterator();
        private boolean canRemove = false;

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public Chromosome next() {
            if (!iter.hasNext()) {
                throw new NoSuchElementException();
            }
            canRemove = true;
            return iter.next();
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("remove() can only be called once after next()");
            }
            iter.remove();
            canRemove = false;
        }
    };
}
