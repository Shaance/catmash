import { Cat } from './cat';

export class CatWithString {
    cat: Cat;
    value: string;

    constructor(cat: Cat, value: string) {
        this.cat = cat;
        this.value = value;
    }
}
