//
// Source code generated by Celerio, a Jaxio product.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Follow us on twitter: @jaxiosoft
// Need commercial support ? Contact us: info@jaxio.com
// Template pack-angular:src/main/webapp/app/entities/entity-line.component.ts.e.vm
//
import {Component, Input} from '@angular/core';
import {Book} from './book';

@Component({
	template: `
        {{ book?.title }} 	`,
	selector: 'book-line',
})
export class BookLineComponent {
    @Input() book : Book;
}
