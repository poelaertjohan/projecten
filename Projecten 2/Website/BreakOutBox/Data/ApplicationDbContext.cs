using Microsoft.EntityFrameworkCore;
using BreakOutBox.Models;
using BreakOutBox;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;

namespace dotnet_g05.Data
{
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public virtual DbSet<Actie> Actie { get; set; }
        public virtual DbSet<Box> Box { get; set; }
        public virtual DbSet<BoxActies> BoxActies { get; set; }
        public virtual DbSet<BoxOef> BoxOef { get; set; }
        public virtual DbSet<Doelstelling> Doelstelling { get; set; }
        public virtual DbSet<Groep> Groep { get; set; }
        public virtual DbSet<GroepOef> GroepOef { get; set; }
        public virtual DbSet<Groepsbewerking> Groepsbewerking { get; set; }
        public virtual DbSet<Mergeall> Mergeall { get; set; }
        public virtual DbSet<Oefening> Oefening { get; set; }
        public virtual DbSet<OefGbw> OefGbw { get; set; }
        public virtual DbSet<Sequence> Sequence { get; set; }
        public virtual DbSet<Sessie> Sessie { get; set; }
        public virtual DbSet<SessieGroep> SessieGroep { get; set; }
        public virtual DbSet<Vooruitgang> Vooruitgang { get; set; }
        public virtual DbSet<BoxToegangscode> BoxToegangscodes { get; set; }

        public ApplicationDbContext()
        {

        }
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Entity<Actie>(entity =>
            {
                entity.HasKey(e => e.actie);

                entity.ToTable("ACTIE");

                entity.Property(e => e.actie)
                    .HasColumnName("ACTIE")
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .ValueGeneratedNever();
            });

            modelBuilder.Entity<Box>(entity =>
            {
                entity.HasKey(e => e.Naam);

                entity.ToTable("BOX");

                entity.Property(e => e.Naam)
                    .HasColumnName("NAAM")
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .ValueGeneratedNever();

                entity.Property(e => e.Omschrijving)
                    .HasColumnName("OMSCHRIJVING")
                    .HasMaxLength(255)
                    .IsUnicode(false);
            });
            
            modelBuilder.Entity<BoxActies>(entity =>
            {
                entity.HasKey(e => new { e.BoxNaam, e.Actie });

                entity.ToTable("Box_Acties");

                entity.Property(e => e.BoxNaam)
                    .HasColumnName("box_naam")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Actie)
                    .HasColumnName("actie_actie")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.HasOne(d => d.ActieActieNavigation)
                    .WithMany(p => p.BoxActies)
                    .HasForeignKey(d => d.Actie)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Box_Acties_actie_actie");

                entity.HasOne(d => d.BoxNaamNavigation)
                    .WithMany(p => p.BoxActies)
                    .HasForeignKey(d => d.BoxNaam)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Box_Acties_box_naam");
            });

            modelBuilder.Entity<BoxOef>(entity =>
            {
                entity.HasKey(e => new { e.BoxNaam, e.OefeningNaam });

                entity.ToTable("Box_oef");

                entity.Property(e => e.BoxNaam)
                    .HasColumnName("box_naam")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.OefeningNaam)
                    .HasColumnName("Oefening_naam")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.HasOne(d => d.BoxNaamNavigation)
                    .WithMany(p => p.BoxOef)
                    .HasForeignKey(d => d.BoxNaam)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Box_oef_box_naam");

                entity.HasOne(d => d.OefeningNaamNavigation)
                    .WithMany(p => p.BoxOef)
                    .HasForeignKey(d => d.OefeningNaam)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Box_oef_Oefening_naam");
            });

            modelBuilder.Entity<Doelstelling>(entity =>
            {
                entity.HasKey(e => e.Doelstelling1);

                entity.ToTable("DOELSTELLING");

                entity.Property(e => e.Doelstelling1)
                    .HasColumnName("DOELSTELLING")
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .ValueGeneratedNever();
            });

            modelBuilder.Entity<Groep>(entity =>
            {
                entity.ToTable("GROEP");

                entity.Property(e => e.Id)
                    .HasColumnName("ID")
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .ValueGeneratedNever();

                entity.Property(e => e.Groepnummer)
                    .HasColumnName("GROEPNUMMER")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Leerlingen)
                    .HasColumnName("LEERLINGEN")
                    .HasMaxLength(255)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<GroepOef>(entity =>
            {
                entity.HasKey(e => new { e.Groep, e.Oefening });

                entity.ToTable("Groep_oef");

                entity.Property(e => e.Groep)
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Oefening)
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.HasOne(d => d.GroepNavigation)
                    .WithMany(p => p.GroepOef)
                    .HasForeignKey(d => d.Groep)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Groep_oef_Groep");

                entity.HasOne(d => d.OefeningNavigation)
                    .WithMany(p => p.GroepOef)
                    .HasForeignKey(d => d.Oefening)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Groep_oef_Oefening");
            });

            modelBuilder.Entity<Groepsbewerking>(entity =>
            {
                entity.HasKey(e => e.Opgave);

                entity.ToTable("GROEPSBEWERKING");

                entity.Property(e => e.Opgave)
                    .HasColumnName("OPGAVE")
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .ValueGeneratedNever();

                entity.Property(e => e.Getal)
                    .HasColumnName("GETAL")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Operator)
                    .HasColumnName("OPERATOR")
                    .HasMaxLength(255)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Mergeall>(entity =>
            {
                entity.ToTable("MERGEALL");

                entity.Property(e => e.Id)
                    .HasColumnName("ID")
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .ValueGeneratedNever();

                entity.Property(e => e.Actiecode).HasColumnName("ACTIECODE");

                entity.Property(e => e.GroepId)
                    .HasColumnName("GROEP_ID")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.GroepsbewerkingOpgave)
                    .HasColumnName("GROEPSBEWERKING_OPGAVE")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.OefNaam)
                    .HasColumnName("OEF_NAAM")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Sessienaam)
                    .HasColumnName("SESSIENAAM")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.HasOne(d => d.Groep)
                    .WithMany(p => p.Mergeall)
                    .HasForeignKey(d => d.GroepId)
                    .HasConstraintName("FK_MERGEALL_GROEP_ID");

                entity.HasOne(d => d.GroepsbewerkingOpgaveNavigation)
                    .WithMany(p => p.Mergeall)
                    .HasForeignKey(d => d.GroepsbewerkingOpgave)
                    .HasConstraintName("MRGLLGRPSBWRKINGOPGAVE");

                entity.HasOne(d => d.OefNaamNavigation)
                    .WithMany(p => p.Mergeall)
                    .HasForeignKey(d => d.OefNaam)
                    .HasConstraintName("FK_MERGEALL_OEF_NAAM");
            });

            modelBuilder.Entity<Oefening>(entity =>
            {
                entity.HasKey(e => e.Naam);

                entity.ToTable("OEFENING");

                entity.Property(e => e.Naam)
                    .HasColumnName("NAAM")
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .ValueGeneratedNever();

                entity.Property(e => e.Antwoord)
                    .HasColumnName("ANTWOORD")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Doelstelling)
                    .HasColumnName("DOELSTELLING_DOELSTELLING")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Feedback)
                    .HasColumnName("FEEDBACK")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Opgave)
                    .HasColumnName("OPGAVE")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Padnaarpdf)
                    .HasColumnName("PADNAARPDF")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Timeleft).HasColumnName("TIMELEFT");

                entity.Property(e => e.Vak)
                    .HasColumnName("VAK")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.HasOne(d => d.DoelstellingDoelstellingNavigation)
                    .WithMany(p => p.Oefening)
                    .HasForeignKey(d => d.Doelstelling)
                    .HasConstraintName("FNNGDLSTLLNGDLSTELLING");
            });

            modelBuilder.Entity<OefGbw>(entity =>
            {
                entity.HasKey(e => new { e.OefeningNaam, e.GroepsBewerkingId });

                entity.ToTable("Oef_Gbw");

                entity.Property(e => e.OefeningNaam)
                    .HasColumnName("Oefening_naam")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.GroepsBewerkingId)
                    .HasColumnName("GroepsBewerking_id")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.HasOne(d => d.GroepsBewerking)
                    .WithMany(p => p.OefGbw)
                    .HasForeignKey(d => d.GroepsBewerkingId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("efGbwGroepsBewerkingid");

                entity.HasOne(d => d.OefeningNaamNavigation)
                    .WithMany(p => p.OefGbw)
                    .HasForeignKey(d => d.OefeningNaam)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Oef_Gbw_Oefening_naam");
            });

            modelBuilder.Entity<Sequence>(entity =>
            {
                entity.HasKey(e => e.SeqName);

                entity.ToTable("SEQUENCE");

                entity.Property(e => e.SeqName)
                    .HasColumnName("SEQ_NAME")
                    .HasMaxLength(50)
                    .IsUnicode(false)
                    .ValueGeneratedNever();

                entity.Property(e => e.SeqCount)
                    .HasColumnName("SEQ_COUNT")
                    .HasColumnType("numeric(28, 0)");
            });

            modelBuilder.Entity<Sessie>(entity =>
            {
                entity.HasKey(e => e.Naam);

                entity.ToTable("SESSIE");

                entity.Property(e => e.Naam)
                    .HasColumnName("NAAM")
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .ValueGeneratedNever();

                entity.Property(e => e.Afstandsonderwijs)
                    .HasColumnName("AFSTANDSONDERWIJS")
                    .HasDefaultValueSql("((0))");

                entity.Property(e => e.Beschikbaarvanaf)
                    .HasColumnName("BESCHIKBAARVANAF")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.BoxNaam)
                    .HasColumnName("BOX_NAAM")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Code)
                    .HasColumnName("CODE")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Omschrijving)
                    .HasColumnName("OMSCHRIJVING")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.HasOne(d => d.BoxNaamNavigation)
                    .WithMany(p => p.Sessie)
                    .HasForeignKey(d => d.BoxNaam)
                    .HasConstraintName("FK_SESSIE_BOX_NAAM");
            });

            modelBuilder.Entity<SessieGroep>(entity =>
            {
                entity.HasKey(e => new { e.SessieNaam, e.GroepId });

                entity.ToTable("Sessie_Groep");

                entity.Property(e => e.SessieNaam)
                    .HasColumnName("sessie_naam")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.GroepId)
                    .HasColumnName("Groep_Id")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.HasOne(d => d.Groep)
                    .WithMany(p => p.SessieGroep)
                    .HasForeignKey(d => d.GroepId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Sessie_Groep_Groep_Id");

                entity.HasOne(d => d.SessieNaamNavigation)
                    .WithMany(p => p.SessieGroep)
                    .HasForeignKey(d => d.SessieNaam)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("SessieGroepsessie_naam");
            });

            modelBuilder.Entity<BoxToegangscode>(entity =>
            {
                entity.HasKey(e => e.BoxNaam);

                entity.ToTable("Box_TOEGANGSCODES");

                entity.Property(e => e.BoxNaam)
                    .HasColumnName("Box_NAAM")
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .ValueGeneratedNever();

                entity.Property(e => e.TOEGANGSCODE)
                    .HasColumnName("TOEGANGSCODES")
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .ValueGeneratedNever();
            });

            modelBuilder.Entity<Vooruitgang>(entity =>
            {
                entity.ToTable("Vooruitgang");

                //Primary key
                entity.HasKey(v => v.Id);

                //Properties
                entity.Property(v => v.Sessienaam)
                    .IsRequired()
                    .HasMaxLength(100);

                entity.Property(v => v.GroepID)
                    .IsRequired()
                    .HasMaxLength(100);

                entity.Property(v => v.OefNummer)
                    .IsRequired()
                    .HasMaxLength(100);

                entity.Property(v => v.Score)
                   .IsRequired()
                   .HasMaxLength(100);

                entity.Property(v => v.IsFrozen)
                   .IsRequired()
                   .HasMaxLength(100);

                entity.Property(v => v.AantalFoutePogingen)
              .IsRequired()
              .HasMaxLength(100);

            });

        }
    }
}
