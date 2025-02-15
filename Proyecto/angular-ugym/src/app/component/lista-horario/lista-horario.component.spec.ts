import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaHorarioComponent } from './lista-horario.component';

describe('ListaHorarioComponent', () => {
  let component: ListaHorarioComponent;
  let fixture: ComponentFixture<ListaHorarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaHorarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaHorarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
