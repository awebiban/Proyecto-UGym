import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarFijaComponent } from './sidebar-fija.component';

describe('SidebarFijaComponent', () => {
  let component: SidebarFijaComponent;
  let fixture: ComponentFixture<SidebarFijaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidebarFijaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarFijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
