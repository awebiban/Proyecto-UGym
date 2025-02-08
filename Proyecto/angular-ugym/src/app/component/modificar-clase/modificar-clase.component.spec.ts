import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificarClaseComponent } from './modificar-clase.component';

describe('ModificarClaseComponent', () => {
  let component: ModificarClaseComponent;
  let fixture: ComponentFixture<ModificarClaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModificarClaseComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModificarClaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
